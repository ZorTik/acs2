package me.zort.acs.plane.api.domain.security;

import org.springframework.security.core.GrantedAuthority;

public enum Privilege implements GrantedAuthority {
    EDIT_REALMS,
    EDIT_USERS;

    @Override
    public String getAuthority() {
        return name();
    }
}
