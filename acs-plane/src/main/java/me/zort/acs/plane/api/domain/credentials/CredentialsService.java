package me.zort.acs.plane.api.domain.credentials;

import java.util.Optional;

public interface CredentialsService {

    Optional<? extends Credentials> getCredentialsByUserId(long userId);

    Optional<? extends Credentials> getCredentialsByUsername(String username);
}
