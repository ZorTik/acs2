package me.zort.acs_plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.*;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs_plane.api.domain.definitions.DefinitionsModification;
import me.zort.acs_plane.api.domain.definitions.DefinitionsModificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsModificationServiceImpl implements DefinitionsModificationService {
    private final DefinitionsObjectFactory objectFactory;

    // TODO: zachytávat modifikace v DefinitionsModificationImpl, pak je vrátit v callbacku a v service broadcastovat
    @Override
    public void modifyDefinitions(
            DefinitionsModel model,
            Consumer<DefinitionsModification> modificationAction,
            Consumer<DefinitionsModel> modifyCallback) throws InvalidDefinitionsException {
        model = cloneModel(model);

        DefinitionsModificationImpl modification = new DefinitionsModificationImpl(objectFactory, model);
        modificationAction.accept(modification);

        modifyCallback.accept(model);
    }

    private DefinitionsModel cloneModel(DefinitionsModel model) {
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

            cloned.getDefaultGrants().add(clonedGrantModel);
        });
    }
}
