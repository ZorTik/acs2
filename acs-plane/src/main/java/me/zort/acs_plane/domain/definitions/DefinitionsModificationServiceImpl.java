package me.zort.acs_plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs_plane.api.domain.definitions.DefinitionsService;
import me.zort.acs_plane.api.domain.definitions.DefinitionsModification;
import me.zort.acs_plane.api.domain.definitions.DefinitionsModificationService;
import me.zort.acs_plane.api.domain.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsModificationServiceImpl implements DefinitionsModificationService {
    private final DefinitionsObjectFactory objectFactory;
    private final DefinitionsService definitionsService;

    private DefinitionsModel cloneModel(DefinitionsModel model) {
        DefinitionsModel cloned = objectFactory.createModel();

        model.getSubjectTypes().forEach(subjectTypeModel -> {
            SubjectTypeDefinitionModel clonedSubjectTypeModel = objectFactory
                    .createSubjectTypeModel(subjectTypeModel.getId());

            // TODO: Populate clonedSubjectTypeModel with other properties from subjectTypeModel

            cloned.getSubjectTypes().add(clonedSubjectTypeModel);
        });
        model.getDefaultGrants().forEach(grantModel -> {
            // TODO
        });
        return cloned;
    }

    @Override
    public void modifyDefinitions(
            Realm realm,
            Consumer<DefinitionsModification> modificationAction,
            Consumer<DefinitionsModel> modifyCallback) throws InvalidDefinitionsException {
        DefinitionsModel model = definitionsService.getDefinitions(realm);
        model = cloneModel(model);

        DefinitionsModification modification = new DefinitionsModificationImpl(model);
        modificationAction.accept(modification);

        modifyCallback.accept(model);
    }
}
