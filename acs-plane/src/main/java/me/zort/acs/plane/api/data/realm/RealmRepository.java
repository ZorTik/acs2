package me.zort.acs.plane.api.data.realm;

import me.zort.acs.plane.data.definitions.model.RealmDocument;

import java.util.List;
import java.util.Optional;

public interface RealmRepository {

    RealmDocument save(RealmDocument entity);

    Optional<RealmDocument> findById(String id);

    void deleteById(String id);

    boolean existsById(String id);

    List<RealmDocument> findAll();
}
