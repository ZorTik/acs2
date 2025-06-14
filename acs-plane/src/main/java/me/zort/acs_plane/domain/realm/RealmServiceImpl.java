package me.zort.acs_plane.domain.realm;

import lombok.RequiredArgsConstructor;
import me.zort.acs_plane.api.domain.definitions.DefinitionsService;
import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.RealmService;
import me.zort.acs_plane.api.domain.realm.exception.RealmAlreadyExistsException;
import me.zort.acs_plane.api.domain.realm.exception.RealmNotExistsException;
import me.zort.acs_plane.domain.realm.event.RealmDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RealmServiceImpl implements RealmService {
    private final DefinitionsService definitionsService;
    private final ApplicationEventPublisher eventPublisher;

    @CacheEvict(value = "realms", key = "#realm")
    @Override
    public Realm createRealm(String realm) throws RealmAlreadyExistsException {
        // TODO
        return null;
    }

    @CacheEvict(value = "realms", key = "#realm.name")
    @Override
    public void deleteRealm(Realm realm) throws RealmNotExistsException {
        if (realm instanceof RealmImpl internal) {
            realm.requireExists();

            // Remove linked definitions
            definitionsService.setDefinitions(realm, null);

            // TODO: Delete realm

            internal.setExists(false);

            eventPublisher.publishEvent(new RealmDeletedEvent(realm));
        } else {
            throw new IllegalArgumentException("RealmService cannot accept this implementation!");
        }
    }

    @Cacheable(value = "realms", key = "#realm", condition = "#result.present")
    @Override
    public Optional<Realm> getRealm(String realm) {
        // TODO
        return Optional.empty();
    }
}
