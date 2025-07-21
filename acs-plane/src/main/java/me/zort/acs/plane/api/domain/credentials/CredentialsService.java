package me.zort.acs.plane.api.domain.credentials;

import java.util.Optional;
import java.util.UUID;

public interface CredentialsService {

    Credentials assignCredentials(UUID userId, String username, String password);

    void deleteCredentialsByUserId(UUID userId);

    Optional<? extends Credentials> getCredentialsByUserId(UUID userId);

    Optional<? extends Credentials> getCredentialsByUsername(String username);
}
