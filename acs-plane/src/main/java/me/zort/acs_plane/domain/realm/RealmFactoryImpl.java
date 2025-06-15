package me.zort.acs_plane.domain.realm;

import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.RealmFactory;
import org.springframework.stereotype.Service;

@Service
public class RealmFactoryImpl implements RealmFactory {

    @Override
    public Realm createRealm(String name) {
        return new RealmImpl(name);
    }
}
