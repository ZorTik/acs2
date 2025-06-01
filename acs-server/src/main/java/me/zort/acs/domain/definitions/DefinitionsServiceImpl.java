package me.zort.acs.domain.definitions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.api.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.api.domain.garbage.ResourceDisposalService;
import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.service.GroupService;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.api.domain.service.SubjectTypeService;
import me.zort.acs.api.domain.definitions.model.DefinitionsModel;
import me.zort.acs.api.domain.definitions.source.DefinitionsSource;
import me.zort.acs.api.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final SubjectTypeService subjectTypeService;
    private final NodeService nodeService;
    private final GroupService groupService;
    private final ResourceDisposalService disposalService;

    private final DefinitionsSource definitionsSource;
    private final DefinitionsValidator definitionsValidator;

    private Map<Pair<SubjectType, SubjectType>, Set<Node>> defaultGrants;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    @Override
    public void refreshDefinitions() {
        try {
            DefinitionsModel model = definitionsSource.getModel();

            log.info("Refreshing definitions...");

            try {
                definitionsValidator.validateDefinitions(model);
            } catch (InvalidDefinitionsException e) {
                log.error("Failed to refresh definitions. Validation failed.", e);
                return;
            }

            // Save subject types and its links
            refreshSubjectTypes(model);
            // Cache default grants
            refreshDefaultGrants(model);

            // Clear caches
            disposalService.disposeBeans(CacheDisposable.class);

            log.info("Definitions refreshed successfully.");
        } catch (Exception e) {
            log.error("Failed to refresh definitions.", e);
        }
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
                .forEach(model -> {
                    Node node = nodeService.createNode(model.getValue());

                    nodeService.assignNode(node, subjectType);
                });
        def.getGroups()
                .forEach(model -> refreshGroup(model, def, subjectType));
    }

    private void refreshGroup(
            GroupDefinitionModel def, SubjectTypeDefinitionModel subjectTypeDef, SubjectType subjectType) {
        Group group = groupService.createGroup(subjectType, def.getName());

        String parentName = def.getParentName();
        if (parentName != null) {
            GroupDefinitionModel parentGroupDef = subjectTypeDef.getGroups()
                    .stream()
                    .filter(groupDef -> groupDef.getName().equals(parentName))
                    .findFirst().orElseThrow();

            refreshGroup(parentGroupDef, subjectTypeDef, subjectType);

            Group parentGroup = groupService.getGroup(subjectType, parentName).orElseThrow();
            groupService.assignGroupParent(group, parentGroup);
        }

        groupService.assignGroupNodes(group, def.getNodes()
                .stream()
                .map(value -> subjectType.getNodeByValue(value).orElseThrow())
                .toList());
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
