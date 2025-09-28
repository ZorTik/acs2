package me.zort.acs.plane.data.security.repository;

import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoApiKeyRepository extends MongoRepository<ApiKeyDocument, Integer> {

    Optional<ApiKeyDocument> findTopByOrderByIdDesc();
}
