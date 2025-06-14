package me.zort.acs_plane.api.domain.realm;

import me.zort.acs_plane.api.domain.realm.exception.RealmAlreadyExistsException;
import me.zort.acs_plane.api.domain.realm.exception.RealmNotExistsException;

import java.util.Optional;

public interface RealmService {

    Realm createRealm(String realm) throws RealmAlreadyExistsException;

    void deleteRealm(Realm realm) throws RealmNotExistsException;

    Optional<Realm> getRealm(String realm);
}
