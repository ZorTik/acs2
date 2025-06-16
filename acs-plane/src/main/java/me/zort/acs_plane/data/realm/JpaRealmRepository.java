package me.zort.acs_plane.data.realm;

import me.zort.acs_plane.api.data.realm.RealmRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRealmRepository extends RealmRepository, JpaRepository<RealmEntity, String> {
}