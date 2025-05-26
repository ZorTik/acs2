package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.zort.acs.api.domain.garbage.ResourceDisposalService;
import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.api.domain.service.SubjectTypeService;
import me.zort.acs.domain.definitions.model.DefinitionsModel;
import me.zort.acs.domain.definitions.source.DefinitionsSource;
import me.zort.acs.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final SubjectTypeService subjectTypeService;
    private final NodeService nodeService;
    private final ResourceDisposalService disposalService;

    private final DefinitionsSource definitionsSource;

    private Map<Pair<SubjectType, SubjectType>, Set<Node>> defaultGrants;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @SneakyThrows(IOException.class)
    @Override
    public void refreshDefinitions() {
        DefinitionsModel model = definitionsSource.getModel();

        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Refreshing definitions...");

        // Save subject types and its links
        refreshSubjectTypes(model);
        // Cache default grants
        refreshDefaultGrants(model);

        // Clear caches
        disposalService.disposeBeans(CacheDisposable.class);

        logger.info("Definitions refreshed successfully.");
    }

    @Override
    public Set<Node> getDefaultGrantedNodes(SubjectType accessorType, SubjectType accessedType) {
        return defaultGrants.get(Pair.of(accessorType, accessedType));
    }

    private void refreshSubjectTypes(DefinitionsModel model) {
        List<SubjectType> localTypes = new ArrayList<>(subjectTypeService.getSubjectTypes());
        model.getSubjectTypes().forEach(def -> {
            String id = def.getId();

            localTypes.removeIf(st -> st.getId().equals(id));

            refreshSubjectType(def);
        });
        localTypes.forEach(subjectTypeService::deleteSubjectType);
    }

    private void refreshSubjectType(SubjectTypeDefinitionModel def) {
        SubjectType subjectType = subjectTypeService.createSubjectType(def.getId());
        def.getNodes()
                .forEach(value -> {
                    Node node = nodeService.createNode(value);

                    nodeService.assignNode(node, subjectType);
                });
    }

    private void refreshDefaultGrants(DefinitionsModel model) {
        defaultGrants = new HashMap<>();
        model.getDefaultGrants().forEach(def -> {
            SubjectType accessor = subjectTypeService.getSubjectType(def.getAccessorType().getId()).orElseThrow();
            SubjectType accessed = subjectTypeService.getSubjectType(def.getAccessedType().getId()).orElseThrow();

            List<Node> nodes = def.getGrantedNodes()
                    .stream()
                    .map(value -> nodeService.getNode(value).orElseThrow())
                    .toList();

            Pair<SubjectType, SubjectType> key = Pair.of(accessor, accessed);

            defaultGrants
                    .computeIfAbsent(key, k -> new HashSet<>())
                    .addAll(nodes);
        });
    }
}
