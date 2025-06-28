package me.zort.acs.plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.plane.api.domain.definitions.DefinitionsModification;
import me.zort.acs.plane.api.domain.definitions.DefinitionsModificationService;
import me.zort.acs.plane.api.domain.definitions.DefinitionsPersistenceService;
import me.zort.acs.plane.api.domain.definitions.DefinitionsService;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.domain.definitions.event.DefinitionsSavedEvent;
import me.zort.acs.plane.domain.definitions.event.DefinitionsModifiedEvent;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final DefinitionsPersistenceService persistenceService;
    private final DefinitionsValidator definitionsValidator;
    private final DefinitionsModificationService modificationService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void modifyDefinitions(
            Realm realm, Consumer<DefinitionsModification> modificationAction) throws InvalidDefinitionsException {
        DefinitionsModel modelBeforeModification = getDefinitions(realm);

        modificationService.modifyDefinitions(modelBeforeModification, modificationAction, modified -> {
            setDefinitions(realm, modified);

            // TODO: Přidat seznam změn, viz DefinitionsModificationServiceImpl
            eventPublisher.publishEvent(new DefinitionsModifiedEvent(realm, modelBeforeModification, modified));
        });
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
