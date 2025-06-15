package me.zort.acs_plane.api.data.realm;

public interface RealmRepository {

    void save(RealmEntity entity);

    void deleteById(String id);
}
