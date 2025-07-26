package me.zort.acs.plane.api.domain.security;

import me.zort.acs.plane.api.domain.user.User;

import java.util.Optional;

public interface CredentialsService {

    Credentials assignCredentials(User user, String username, String password);

    void deleteCredentialsByUser(User user);

    Optional<? extends Credentials> getCredentialsByUser(User user);

    Optional<? extends Credentials> getCredentialsByUsername(String username);
}
