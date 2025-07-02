package me.zort.acs.plane.api.data.realm;

import me.zort.acs.plane.data.definitions.model.RealmModel;

import java.util.Optional;

public interface RealmRepository {

    RealmModel save(RealmModel entity);

    Optional<RealmModel> findById(String id);

    void deleteById(String id);

    boolean existsById(String id);
}
