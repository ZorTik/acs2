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

    /**
     * Get the hashed password of a user by their ID.
     *
     * @param userId the ID of the user whose password is to be retrieved
     * @return the hashed password of the user, or null if the user does not exist
     */
    String getEncodedPassword(long userId);
}
