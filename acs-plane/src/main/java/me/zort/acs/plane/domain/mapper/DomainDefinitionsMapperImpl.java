package me.zort.acs.plane.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.model.*;
import me.zort.acs.plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs.plane.api.domain.mapper.DefinitionsMapper;
import me.zort.acs.plane.data.definitions.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainDefinitionsMapperImpl implements DefinitionsMapper {
    private final DefinitionsObjectFactory definitionsObjectFactory;

    @Override
    public DefinitionsModel toDomain(RealmModel entity) {
        Map<String, SubjectTypeDefinitionModel> subjectTypeDefs = new HashMap<>();
        entity.getSubjectTypes().forEach(subjectTypeEntity -> {
            SubjectTypeDefinitionModel model = definitionsObjectFactory
                    .createSubjectTypeModel(subjectTypeEntity.getName());
            subjectTypeEntity.getNodes().forEach(nodeEntity -> {
                NodeDefinitionModel nodeModel = definitionsObjectFactory.createNodeModel(nodeEntity.getValue());

                model.getNodes().add(nodeModel);
            });
            subjectTypeEntity.getGroups().forEach(groupEntity -> {
                GroupDefinitionModel groupModel = definitionsObjectFactory
                        .createGroupModel(groupEntity.getId(), groupEntity.getParent());
                groupModel.getNodes().addAll(groupEntity.getNodes());

                model.getGroups().add(groupModel);
            });

            subjectTypeDefs.put(model.getId(), model);
        });

        DefinitionsModel model = definitionsObjectFactory.createModel();
        model.getSubjectTypes().addAll(subjectTypeDefs.values());

        entity.getDefaultGrants().forEach(grantEntity -> {
            DefaultGrantsDefinitionModel grantModel = definitionsObjectFactory.createDefaultGrantsModel(
                    subjectTypeDefs.get(grantEntity.getFrom()),
                    subjectTypeDefs.get(grantEntity.getTo()));
            grantModel.getGrantedNodes().addAll(grantEntity.getNodes());
            grantModel.getGrantedGroups().addAll(grantEntity.getGroups());

            model.getDefaultGrants().add(grantModel);
        });

        return model;
    }

    @Override
    public void toPersistence(DefinitionsModel model, RealmModel entity) {
        entity.getSubjectTypes().clear();
        entity.getDefaultGrants().clear();

        model.getSubjectTypes().forEach(subjectType ->
                entity.getSubjectTypes().add(toPersistenceSubjectType(subjectType)));
        model.getDefaultGrants().forEach(grant ->
                entity.getDefaultGrants().add(toPersistenceDefaultGrant(grant)));
    }

    @NotNull
    private DefaultGrantModel toPersistenceDefaultGrant(DefaultGrantsDefinitionModel grant) {
        DefaultGrantModel grantModel = new DefaultGrantModel();
        grantModel.setFrom(grant.getAccessorType().getId());
        grantModel.setTo(grant.getAccessedType().getId());
        grantModel.setNodes(grant.getGrantedNodes());
        grantModel.setGroups(grant.getGrantedGroups());
        return grantModel;
    }

    @NotNull
    private SubjectTypeModel toPersistenceSubjectType(SubjectTypeDefinitionModel subjectType) {
        SubjectTypeModel subjectTypeModel = new SubjectTypeModel();
        subjectTypeModel.setName(subjectType.getId());
        subjectTypeModel.setNodes(subjectType.getNodes()
                .stream()
                .map(this::toPersistenceNode).toList());
        subjectTypeModel.setGroups(subjectType.getGroups()
                .stream()
                .map(this::toPersistenceGroup).toList());
        return subjectTypeModel;
    }

    @NotNull
    private NodeModel toPersistenceNode(NodeDefinitionModel node) {
        NodeModel nodeModel = new NodeModel();
        nodeModel.setValue(node.getValue());

        return nodeModel;
    }

    @NotNull
    private GroupModel toPersistenceGroup(GroupDefinitionModel group) {
        GroupModel groupModel = new GroupModel();
        groupModel.setId(group.getName());
        groupModel.setParent(group.getParentName());
        groupModel.setNodes(new ArrayList<>(group.getNodes()));

        return groupModel;
    }
}
