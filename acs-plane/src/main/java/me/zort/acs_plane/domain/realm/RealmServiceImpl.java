package me.zort.acs_plane.domain.realm;

import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.RealmService;
import me.zort.acs_plane.api.domain.realm.exception.RealmAlreadyExistsException;
import me.zort.acs_plane.api.domain.realm.exception.RealmNotExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RealmServiceImpl implements RealmService {
    // TODO: link realmů objektů který nastaví existing na false pokud přijde delete event

    @Override
    public Realm createRealm(String realm) throws RealmAlreadyExistsException {
        // TODO
        return null;
    }

    @Override
    public void deleteRealm(Realm realm) throws RealmNotExistsException {
        realm.requireExists();

        // TODO
    }

    @Override
    public Optional<Realm> getRealm(String realm) {
        // TODO
        return Optional.empty();
    }
}
