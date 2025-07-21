package me.zort.acs.plane.api.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(CreateUserArgs args) throws IllegalArgumentException;

    void deleteUserById(UUID id);

    Optional<? extends User> getUserById(UUID id);

    long getUserCount();
}
