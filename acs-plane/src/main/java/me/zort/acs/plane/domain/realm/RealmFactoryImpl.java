package me.zort.acs.plane.domain.realm;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.definitions.DefinitionsObjectFactory;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.RealmFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RealmFactoryImpl implements RealmFactory {
    private final DefinitionsObjectFactory definitionsObjectFactory;

    @Override
    public Realm createRealm(String name) {
        return new RealmImpl(name, definitionsObjectFactory.createModel());
    }
}
