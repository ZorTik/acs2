package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.zort.acs.config.properties.AcsConfigurationProperties;
import me.zort.acs.domain.definitions.model.DefinitionsModel;
import me.zort.acs.domain.definitions.source.DefinitionsSource;
import me.zort.acs.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs.domain.model.Node;
import me.zort.acs.api.domain.model.SubjectLike;
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
public class DefinitionsService {
    private final SubjectTypeService subjectTypeService;
    private final NodeService nodeService;
    private final AcsConfigurationProperties properties;

    private final DefinitionsSource definitionsSource;

    private Map<Pair<SubjectType, SubjectType>, Set<Node>> defaultGrants;

    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows(IOException.class)
    public void refresh() {
        DefinitionsModel model = definitionsSource.getModel();

        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Refreshing definitions...");

        // Save subject types and its links
        refreshSubjectTypes(model);
        // Cache default grants
        refreshDefaultGrants(model);

        logger.info("Definitions refreshed successfully.");
    }

    public boolean checkDefaultGrant(SubjectLike from, SubjectLike to, Node node) {
        Set<Node> nodes = defaultGrants.get(Pair.of(from.getSubjectType(), to.getSubjectType()));

        if (nodes == null) {
            return false;
        }
        return nodes
                .stream()
                .anyMatch(defNode -> defNode.isParentOf(node, properties.getDelimiter()));
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
        subjectTypeService.createSubjectType(def.getId()).ifPresent(subjectType -> {
            def.getNodes()
                    .forEach(value -> {
                        Optional<Node> nodeOptional = nodeService.createNode(value);
                        if (nodeOptional.isEmpty()) {
                            return;
                        }

                        nodeService.assignNode(nodeOptional.get(), subjectType);
                    });
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
