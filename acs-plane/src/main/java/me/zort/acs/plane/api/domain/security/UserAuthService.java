package me.zort.acs.plane.api.domain.security;

import me.zort.acs.plane.api.domain.user.User;

import java.util.Optional;

public interface UserAuthService {

    /**
     * Get a user by their principal (username/email).
     *
     * @param principal the principal identifier of the user
     * @return the User object associated with the given principal, or null if not found
     */
    Optional<? extends User> getUserByPrincipal(String principal);
}
