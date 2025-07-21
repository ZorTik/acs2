package me.zort.acs.plane.api.domain.user;

import me.zort.acs.plane.api.domain.security.Role;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(CreateUserArgs args) throws IllegalArgumentException;

    void deleteUser(User user);

    void setRole(User user, Role role);

    Optional<? extends User> getUserById(UUID id);

    long getUserCount();
}
