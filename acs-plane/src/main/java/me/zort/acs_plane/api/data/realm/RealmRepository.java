package me.zort.acs_plane.api.data.realm;

import java.util.Optional;

public interface RealmRepository {

    RealmEntity save(RealmEntity entity);

    Optional<RealmEntity> findById(String id);

    void deleteById(String id);
}
