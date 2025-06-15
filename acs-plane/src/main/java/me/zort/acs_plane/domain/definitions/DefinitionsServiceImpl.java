package me.zort.acs_plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs_plane.api.domain.definitions.*;
import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.domain.definitions.event.DefinitionsSavedEvent;
import me.zort.acs_plane.domain.definitions.event.DefinitionsModifiedEvent;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final DefinitionsRepository persistenceService;
    private final DefinitionsValidator definitionsValidator;
    private final DefinitionsModificationService modificationService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void modifyDefinitions(
            Realm realm, Consumer<DefinitionsModification> modificationAction) throws InvalidDefinitionsException {
        DefinitionsModel modelBeforeModification = getDefinitions(realm);
        Consumer<DefinitionsModel> modificationCallback = modified -> {
            setDefinitions(realm, modified);

            // TODO: Přidat seznam změn, viz DefinitionsModificationServiceImpl
            eventPublisher.publishEvent(new DefinitionsModifiedEvent(realm, modelBeforeModification, modified));
        };

        modificationService.modifyDefinitions(modelBeforeModification, modificationAction, modificationCallback);
    }

    @Override
    public void setDefinitions(Realm realm, @Nullable DefinitionsModel model) throws InvalidDefinitionsException {
        if (model != null) {
            definitionsValidator.validateDefinitions(model);
        }

        persistenceService.saveDefinitions(realm.getName(), model);

        eventPublisher.publishEvent(new DefinitionsSavedEvent(realm, model));
    }

    @Unmodifiable
    @Override
    public DefinitionsModel getDefinitions(Realm realm) {
        realm.requireExists();

        return persistenceService.loadOrCreateDefinitions(realm.getName());
    }
}
