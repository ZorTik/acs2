package me.zort.acs.plane.api.domain.realm;

import java.util.Optional;

public interface RealmPersistenceService {

    void saveRealm(Realm realm);

    void deleteRealm(String id);

    Optional<Realm> getRealm(String id);
}
