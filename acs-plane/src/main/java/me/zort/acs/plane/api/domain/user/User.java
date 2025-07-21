package me.zort.acs.plane.api.domain.user;

import me.zort.acs.plane.api.domain.security.Role;

import java.util.UUID;

public interface User {

    UUID getId();

    String getDisplayName();

    void setRole(Role role);

    Role getRole();
}
