package me.zort.acs.plane.data.credentials;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CredentialsRepository extends MongoRepository<CredentialsDocument, Long> {

    Optional<CredentialsDocument> findByUserId(long userId);

    Optional<CredentialsDocument> findByUsername(String username);
}
