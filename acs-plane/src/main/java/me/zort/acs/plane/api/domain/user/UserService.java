package me.zort.acs.plane.api.domain.user;

import java.util.Optional;

public interface UserService {

    Optional<? extends User> getUserById(long id);

    long getUserCount();
}
