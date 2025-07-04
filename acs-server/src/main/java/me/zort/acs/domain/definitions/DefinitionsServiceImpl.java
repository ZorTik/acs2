package me.zort.acs.domain.definitions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.definitions.DefinitionsSynchronizer;
import me.zort.acs.api.domain.definitions.SynchronizationContext;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.api.domain.garbage.ResourceDisposalService;
import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.definitions.DefinitionsService;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.source.DefinitionsSource;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final ResourceDisposalService disposalService;

    private final DefinitionsSource definitionsSource;
    private final DefinitionsValidator definitionsValidator;
    private final DefinitionsSynchronizer definitionsSynchronizer;

    private Map<Pair<SubjectType, SubjectType>, Set<Node>> defaultGrants;
    private Map<Pair<SubjectType, SubjectType>, Set<Group>> defaultGrantedGroups;

    @Transactional
    @Override
    public void refreshDefinitions() {
        DefinitionsModel model;
        try {
            model = definitionsSource.getModel();
        } catch (IOException e) {
            log.error("Failed to fetch definitions from source.", e);

            return;
        }

        refreshDefinitions(model);
    }

    @Override
    public void refreshDefinitions(DefinitionsModel model) {
        try {
            log.info("Refreshing definitions...");

            try {
                definitionsValidator.validateDefinitions(model);
            } catch (InvalidDefinitionsException e) {
                log.error("Failed to refresh definitions. Validation failed.", e);
                return;
            }

            defaultGrants = new HashMap<>();
            defaultGrantedGroups = new HashMap<>();

            // Synchronize database with definitions
            definitionsSynchronizer.synchronizeSystemDefinitions(model, createSynchronizationContext());

            // Clear caches
            disposalService.disposeBeans(CacheDisposable.class);

            log.info("Definitions refreshed successfully.");
        } catch (Exception e) {
            log.error("Failed to refresh definitions.", e);

            throw new RuntimeException(e);
        }
    }

    private @NotNull SynchronizationContext createSynchronizationContext() {
        return (accessor, accessed, nodes, groups) -> {
            Pair<SubjectType, SubjectType> key = Pair.of(accessor, accessed);

            defaultGrants
                    .computeIfAbsent(key, k -> new HashSet<>())
                    .addAll(nodes);
            defaultGrantedGroups
                    .computeIfAbsent(key, k -> new HashSet<>())
                    .addAll(groups);
        };
    }

    @Override
    public Set<Node> getDefaultGrantedNodes(SubjectType accessorType, SubjectType accessedType) {
        return defaultGrants.getOrDefault(Pair.of(accessorType, accessedType), new HashSet<>());
    }

    @Override
    public Set<Group> getDefaultGrantedGroups(SubjectType accessorType, SubjectType accessedType) {
        return defaultGrantedGroups.getOrDefault(Pair.of(accessorType, accessedType), new HashSet<>());
    }
}
