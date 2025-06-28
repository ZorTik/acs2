package me.zort.acs.plane.domain.realm;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.definitions.DefinitionsService;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmFactory;
import me.zort.acs.plane.api.domain.realm.RealmPersistenceService;
import me.zort.acs.plane.api.domain.realm.RealmService;
import me.zort.acs.plane.api.domain.realm.exception.RealmAlreadyExistsException;
import me.zort.acs.plane.api.domain.realm.exception.RealmNotExistsException;
import me.zort.acs.plane.domain.realm.event.RealmCreatedEvent;
import me.zort.acs.plane.domain.realm.event.RealmDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RealmServiceImpl implements RealmService {
    private final RealmPersistenceService persistenceService;
    private final RealmFactory realmFactory;
    private final DefinitionsService definitionsService;
    private final ApplicationEventPublisher eventPublisher;

    @CacheEvict(value = "realms", key = "#realm")
    @Override
    public Realm createRealm(String realm) throws RealmAlreadyExistsException {
        getRealm(realm).ifPresent(existingRealm -> {
            throw new RealmAlreadyExistsException(existingRealm);
        });

        Realm newRealm = realmFactory.createRealm(realm);
        persistenceService.saveRealm(newRealm);

        eventPublisher.publishEvent(new RealmCreatedEvent(newRealm));
        return newRealm;
    }

    @Override
    public void deleteRealm(Realm realm) throws RealmNotExistsException {
        realm.requireExists();

        // Remove linked definitions
        definitionsService.setDefinitions(realm, null);
        persistenceService.deleteRealm(realm.getName());

        eventPublisher.publishEvent(new RealmDeletedEvent(realm));
    }

    @Override
    public Optional<Realm> getRealm(String realm) {
        return persistenceService.getRealm(realm);
    }
}
