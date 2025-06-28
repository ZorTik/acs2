package me.zort.acs.plane.api.data.realm;

import me.zort.acs.plane.data.realm.RealmEntity;

import java.util.Optional;

public interface RealmRepository {

    RealmEntity save(RealmEntity entity);

    Optional<RealmEntity> findById(String id);

    void deleteById(String id);
}
