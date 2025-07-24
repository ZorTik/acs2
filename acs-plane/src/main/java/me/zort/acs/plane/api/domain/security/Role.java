package me.zort.acs.plane.api.domain.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Role {
    ADMIN(Privilege.values()),
    USER;

    private final Privilege[] privileges;

    Role(Privilege... privileges) {
        this.privileges = privileges;
    }

    public static @NotNull Role defaultRole() {
        return USER;
    }

    @Unmodifiable
    public List<Privilege> getPrivileges() {
        return Collections.unmodifiableList(Arrays.asList(privileges));
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
