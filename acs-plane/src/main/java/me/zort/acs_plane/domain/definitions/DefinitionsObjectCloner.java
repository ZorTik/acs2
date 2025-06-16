package me.zort.acs_plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.ObjectCloner;
import me.zort.acs.core.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsObjectCloner implements ObjectCloner<DefinitionsModel> {
    private final DefinitionsObjectFactory objectFactory;

    @Override
    public DefinitionsModel cloneObject(DefinitionsModel model) {
        DefinitionsModel cloned = objectFactory.createModel();

        Map<String, SubjectTypeDefinitionModel> subjectTypesRegistry = new HashMap<>();

        cloneAndPopulateWithSubjectTypes(model, cloned, subjectTypesRegistry);
        cloneAndPopulateWithDefaultGrants(model, cloned, subjectTypesRegistry);
        return cloned;
    }

    private void cloneAndPopulateWithSubjectTypes(
            DefinitionsModel model,
            DefinitionsModel cloned, Map<String, SubjectTypeDefinitionModel> subjectTypesRegistry) {
        model.getSubjectTypes().forEach(subjectTypeModel -> {
            SubjectTypeDefinitionModel clonedSubjectTypeModel = objectFactory
                    .createSubjectTypeModel(subjectTypeModel.getId());

            subjectTypeModel.getNodes()
                    .stream()
                    .map(nodeModel -> objectFactory.createNodeModel(nodeModel.getValue()))
                    .forEach(nodeModel -> clonedSubjectTypeModel.getNodes().add(nodeModel));
            subjectTypeModel.getGroups()
                    .stream()
                    .map(groupModel -> {
                        GroupDefinitionModel clonedGroupModel = objectFactory.createGroupModel(
                                groupModel.getName(), groupModel.getParentName());
                        clonedGroupModel.getNodes().addAll(groupModel.getNodes());

                        return clonedGroupModel;
                    })
                    .forEach(groupModel -> clonedSubjectTypeModel.getGroups().add(groupModel));

            cloned.getSubjectTypes().add(clonedSubjectTypeModel);
            subjectTypesRegistry.put(clonedSubjectTypeModel.getId(), clonedSubjectTypeModel);
        });
    }

    private void cloneAndPopulateWithDefaultGrants(
            DefinitionsModel model,
            DefinitionsModel cloned, Map<String, SubjectTypeDefinitionModel> subjectTypesRegistry) {
        model.getDefaultGrants().forEach(grantModel -> {
            SubjectTypeDefinitionModel clonedAccessorTypeModel = subjectTypesRegistry.get(
                    grantModel.getAccessorType().getId());
            SubjectTypeDefinitionModel clonedAccessedTypeModel = subjectTypesRegistry.get(
                    grantModel.getAccessedType().getId());

            if (clonedAccessorTypeModel == null || clonedAccessedTypeModel == null) {
                // This is validated by the external validator, but we handle it gracefully here.
                return;
            }

            DefaultGrantsDefinitionModel clonedGrantModel = objectFactory.createDefaultGrantsModel(
                    clonedAccessorTypeModel, clonedAccessedTypeModel);
            clonedGrantModel.getGrantedNodes().addAll(grantModel.getGrantedNodes());
            clonedGrantModel.getGrantedGroups().addAll(grantModel.getGrantedGroups());

            cloned.getDefaultGrants().add(clonedGrantModel);
        });
    }
}
