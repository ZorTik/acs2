package me.zort.acs_plane.api.domain.realm;

import me.zort.acs_plane.api.domain.realm.exception.RealmAlreadyExistsException;

import java.util.Optional;

public interface RealmService {

    Realm createRealm(String realm) throws RealmAlreadyExistsException;

    Optional<Realm> getRealm(String realm);
}
