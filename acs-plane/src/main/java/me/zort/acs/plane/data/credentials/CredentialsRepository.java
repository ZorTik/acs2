package me.zort.acs.plane.data.credentials;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CredentialsRepository extends MongoRepository<CredentialsDocument, Long> {

    void deleteByUserId(UUID userId);

    Optional<CredentialsDocument> findByUserId(UUID userId);

    Optional<CredentialsDocument> findByUsername(String username);
}
