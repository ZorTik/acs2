package me.zort.acs.domain.definitions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.group.CreateGroupOptions;
import me.zort.acs.api.domain.group.exception.GroupAlreadyExistsException;
import me.zort.acs.api.domain.subjecttype.CreateSubjectTypeOptions;
import me.zort.acs.api.domain.subjecttype.exception.SubjectTypeAlreadyExistsException;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.api.domain.garbage.ResourceDisposalService;
import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.api.domain.group.GroupService;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.source.DefinitionsSource;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.service.SubjectTypeServiceImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final SubjectTypeServiceImpl subjectTypeService;
    private final NodeService nodeService;
    private final GroupService groupService;
    private final ResourceDisposalService disposalService;

    private final DefinitionsSource definitionsSource;
    private final DefinitionsValidator definitionsValidator;

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

    @Transactional(rollbackFor = {RuntimeException.class})
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

            // Save subject types and its links
            refreshSubjectTypes(model);
            // Cache default grants
            refreshDefaultGrants(model);

            // Clear caches
            disposalService.disposeBeans(CacheDisposable.class);

            log.info("Definitions refreshed successfully.");
        } catch (Exception e) {
            log.error("Failed to refresh definitions.", e);

            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Node> getDefaultGrantedNodes(SubjectType accessorType, SubjectType accessedType) {
        return defaultGrants.getOrDefault(Pair.of(accessorType, accessedType), new HashSet<>());
    }

    @Override
    public Set<Group> getDefaultGrantedGroups(SubjectType accessorType, SubjectType accessedType) {
        return defaultGrantedGroups.getOrDefault(Pair.of(accessorType, accessedType), new HashSet<>());
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
        defaultGrants = new HashMap<>();
        defaultGrantedGroups = new HashMap<>();
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

            Pair<SubjectType, SubjectType> key = Pair.of(accessor, accessed);

            defaultGrants
                    .computeIfAbsent(key, k -> new HashSet<>())
                    .addAll(nodes);
            defaultGrantedGroups
                    .computeIfAbsent(key, k -> new HashSet<>())
                    .addAll(groups);
        });
    }
}
