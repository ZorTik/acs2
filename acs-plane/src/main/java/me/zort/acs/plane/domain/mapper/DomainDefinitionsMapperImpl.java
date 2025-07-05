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
    public DefinitionsModel toDomain(RealmDocument entity) {
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
    public void toPersistence(DefinitionsModel model, RealmDocument entity) {
        entity.getSubjectTypes().clear();
        entity.getDefaultGrants().clear();

        model.getSubjectTypes().forEach(subjectType ->
                entity.getSubjectTypes().add(toPersistenceSubjectType(subjectType)));
        model.getDefaultGrants().forEach(grant ->
                entity.getDefaultGrants().add(toPersistenceDefaultGrant(grant)));
    }

    @NotNull
    private DefaultGrantDocument toPersistenceDefaultGrant(DefaultGrantsDefinitionModel grant) {
        DefaultGrantDocument grantModel = new DefaultGrantDocument();
        grantModel.setFrom(grant.getAccessorType().getId());
        grantModel.setTo(grant.getAccessedType().getId());
        grantModel.setNodes(grant.getGrantedNodes());
        grantModel.setGroups(grant.getGrantedGroups());
        return grantModel;
    }

    @NotNull
    private SubjectTypeDocument toPersistenceSubjectType(SubjectTypeDefinitionModel subjectType) {
        SubjectTypeDocument subjectTypeDocument = new SubjectTypeDocument();
        subjectTypeDocument.setName(subjectType.getId());
        subjectTypeDocument.setNodes(subjectType.getNodes()
                .stream()
                .map(this::toPersistenceNode).toList());
        subjectTypeDocument.setGroups(subjectType.getGroups()
                .stream()
                .map(this::toPersistenceGroup).toList());
        return subjectTypeDocument;
    }

    @NotNull
    private NodeDocument toPersistenceNode(NodeDefinitionModel node) {
        NodeDocument nodeDocument = new NodeDocument();
        nodeDocument.setValue(node.getValue());

        return nodeDocument;
    }

    @NotNull
    private GroupDocument toPersistenceGroup(GroupDefinitionModel group) {
        GroupDocument groupDocument = new GroupDocument();
        groupDocument.setId(group.getName());
        groupDocument.setParent(group.getParentName());
        groupDocument.setNodes(new ArrayList<>(group.getNodes()));

        return groupDocument;
    }
}
