package me.zort.acs.plane.domain.realm;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmFactory;
import me.zort.acs.plane.api.domain.realm.RealmPersistenceService;
import me.zort.acs.plane.api.domain.realm.RealmService;
import me.zort.acs.plane.api.domain.realm.exception.RealmAlreadyExistsException;
import me.zort.acs.plane.api.domain.realm.exception.RealmNotExistsException;
import me.zort.acs.plane.domain.realm.event.RealmCreatedEvent;
import me.zort.acs.plane.domain.realm.event.RealmDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RealmServiceImpl implements RealmService {
    private final RealmPersistenceService persistenceService;
    private final RealmFactory realmFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Realm createRealm(String realm) throws RealmAlreadyExistsException {
        if (persistenceService.existsRealm(realm)) {
            throw new RealmAlreadyExistsException();
        }

        Realm newRealm = realmFactory.createRealm(realm);
        persistenceService.saveRealm(newRealm);

        eventPublisher.publishEvent(new RealmCreatedEvent(newRealm));
        return newRealm;
    }

    @Override
    public void deleteRealm(Realm realm) throws RealmNotExistsException {
        realm.requireExists();

        persistenceService.deleteRealm(realm.getName());

        eventPublisher.publishEvent(new RealmDeletedEvent(realm));
    }

    @Override
    public Optional<Realm> getRealm(String realm) {
        return persistenceService.getRealm(realm);
    }
}
