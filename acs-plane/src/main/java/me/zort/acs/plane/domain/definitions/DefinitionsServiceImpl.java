package me.zort.acs.plane.domain.definitions;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.plane.api.domain.definitions.*;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmPersistenceService;
import me.zort.acs.plane.domain.definitions.event.DefinitionsSavedEvent;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsServiceImpl implements DefinitionsService {
    private final DefinitionsValidator definitionsValidator;
    private final DefinitionsObjectFactory definitionsObjectFactory;
    private final DefinitionsObjectCloner definitionsObjectCloner;
    private final RealmPersistenceService realmPersistenceService;
    private final ApplicationEventPublisher eventPublisher;

    // TODO: Předělat na String realmName
    /**
     * @see DefinitionsService#setDefinitions(Realm, DefinitionsModel)
     */
    @Override
    public void setDefinitions(Realm realm, @Nullable DefinitionsModel model) throws InvalidDefinitionsException {
        realm.requireExists();

        DefinitionsModel modelToSet;
        if (model == null) {
            modelToSet = definitionsObjectFactory.createModel();
        } else {
            definitionsValidator.validateDefinitions(model);
            modelToSet = model;
        }

        definitionsObjectCloner.copyInto(modelToSet, realm.getDefinitionsModel());
        realmPersistenceService.saveRealm(realm);

        eventPublisher.publishEvent(new DefinitionsSavedEvent(realm, model));
    }
}
