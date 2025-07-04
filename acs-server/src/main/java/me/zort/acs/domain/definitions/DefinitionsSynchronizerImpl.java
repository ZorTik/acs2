package me.zort.acs.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.definitions.DefinitionsSynchronizer;
import me.zort.acs.api.domain.definitions.SynchronizationContext;
import me.zort.acs.api.domain.group.CreateGroupOptions;
import me.zort.acs.api.domain.group.GroupService;
import me.zort.acs.api.domain.group.exception.GroupAlreadyExistsException;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.api.domain.subjecttype.CreateSubjectTypeOptions;
import me.zort.acs.api.domain.subjecttype.SubjectTypeService;
import me.zort.acs.api.domain.subjecttype.exception.SubjectTypeAlreadyExistsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsSynchronizerImpl implements DefinitionsSynchronizer {
    private final SubjectTypeService subjectTypeService;
    private final NodeService nodeService;
    private final GroupService groupService;

    private SynchronizationContext context;

    @Transactional
    @Override
    public synchronized void synchronizeSystemDefinitions(
            DefinitionsModel synchronizeWith, SynchronizationContext context) {
        this.context = context;

        // Save subject types and its links
        refreshSubjectTypes(synchronizeWith);
        // Cache default grants
        refreshDefaultGrants(synchronizeWith);
    }

    private void refreshSubjectTypes(DefinitionsModel model) {
        List<SubjectType> localTypes = subjectTypeService.getSubjectTypes();
        model.getSubjectTypes().forEach(def -> {
            String id = def.getId();

            localTypes.removeIf(st -> st.getId().equals(id));

            refreshSubjectType(def);
        });

        localTypes.forEach(subjectTypeService::deleteSubjectType);
    }

    private void refreshSubjectType(SubjectTypeDefinitionModel def) {
        List<Node> nodesToAssign = def.getNodes()
                .stream()
                .map(model -> nodeService.createNode(model.getValue())).toList();

        SubjectType subjectType;
        try {
            subjectType = subjectTypeService.createSubjectType(def.getId(), CreateSubjectTypeOptions.builder()
                    .nodes(nodesToAssign).build());
        } catch (SubjectTypeAlreadyExistsException e) {
            subjectType = subjectTypeService.getSubjectType(e.getExistingId()).orElseThrow();

            subjectTypeService.assignSubjectTypeNodes(subjectType, nodesToAssign);
        }

        for (GroupDefinitionModel model : def.getGroups()) {
            refreshGroup(model, def, subjectType);
        }
    }

    private Group refreshGroup(
            GroupDefinitionModel def, SubjectTypeDefinitionModel subjectTypeDef, SubjectType subjectType) {
        List<Node> nodesToAssign = def.getNodes()
                .stream()
                .map(value -> subjectType.getNodeByValue(value).orElseThrow())
                .toList();

        Group parentGroup = null;
        String parentName = def.getParentName();
        if (parentName != null) {
            GroupDefinitionModel parentGroupDef = subjectTypeDef.getGroups()
                    .stream()
                    .filter(groupDef -> groupDef.getName().equals(parentName))
                    .findFirst().orElseThrow();

            parentGroup = refreshGroup(parentGroupDef, subjectTypeDef, subjectType);
        }

        Group group;
        try {
            group = groupService.createGroup(subjectType, def.getName(), CreateGroupOptions.builder()
                    .parentGroup(parentGroup)
                    .nodes(nodesToAssign).build());
        } catch (GroupAlreadyExistsException e) {
            group = e.getExisting();

            groupService.assignGroupNodes(group, nodesToAssign);

            if (parentGroup != null) {
                groupService.assignGroupParent(group, parentGroup);
            }
        }

        return group;
    }

    private void refreshDefaultGrants(DefinitionsModel model) {
        model.getDefaultGrants().forEach(def -> {
            SubjectType accessor = subjectTypeService.getSubjectType(def.getAccessorType().getId()).orElseThrow();
            SubjectType accessed = subjectTypeService.getSubjectType(def.getAccessedType().getId()).orElseThrow();

            List<Node> nodes = def.getGrantedNodes()
                    .stream()
                    .map(value -> nodeService.getNode(value).orElseThrow())
                    .toList();
            List<Group> groups = def.getGrantedGroups()
                    .stream()
                    .map(name -> groupService.getGroup(accessed, name).orElseThrow())
                    .toList();

            context.onDefaultGrantDiscovered(accessor, accessed, nodes, groups);
        });
    }
}
