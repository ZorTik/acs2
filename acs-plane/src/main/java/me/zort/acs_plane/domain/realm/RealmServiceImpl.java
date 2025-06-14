package me.zort.acs_plane.domain.realm;

import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.RealmService;
import me.zort.acs_plane.api.domain.realm.exception.RealmAlreadyExistsException;

import java.util.Optional;

public class RealmServiceImpl implements RealmService {
    // TODO: link realmů objektů který nastaví existing na false pokud přijde delete event

    @Override
    public Realm createRealm(String realm) throws RealmAlreadyExistsException {
        // TODO
        return null;
    }

    @Override
    public Optional<Realm> getRealm(String realm) {
        // TODO
        return Optional.empty();
    }
}
