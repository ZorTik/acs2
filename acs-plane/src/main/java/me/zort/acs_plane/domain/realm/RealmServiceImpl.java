package me.zort.acs_plane.domain.realm;

import lombok.RequiredArgsConstructor;
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
    private final ApplicationEventPublisher eventPublisher;

    @CacheEvict(value = "realms", key = "#realm")
    @Override
    public Realm createRealm(String realm) throws RealmAlreadyExistsException {
        // TODO
        return null;
    }

    @CacheEvict(value = "realms", key = "#realm")
    @Override
    public void deleteRealm(String realm) throws RealmNotExistsException {
        Realm realmObj = getRealm(realm).orElseThrow(() -> new RealmNotExistsException(realm));
        // Ensure, even tho it is possibly unnecessary
        realmObj.requireExists();

        // TODO: Delete realm

        ((RealmImpl) realmObj).setExists(false);
        eventPublisher.publishEvent(new RealmDeletedEvent(realmObj));
    }

    @Cacheable(value = "realms", key = "#realm", condition = "#result.present")
    @Override
    public Optional<Realm> getRealm(String realm) {
        // TODO
        return Optional.empty();
    }
}
