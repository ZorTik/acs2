package me.zort.acs.plane.data.definitions.repository;

import me.zort.acs.plane.data.definitions.model.RealmDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRealmRepository extends MongoRepository<RealmDocument, String> {
}