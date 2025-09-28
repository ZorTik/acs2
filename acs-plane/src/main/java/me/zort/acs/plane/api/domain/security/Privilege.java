package me.zort.acs.plane.api.domain.security;

import org.springframework.security.core.GrantedAuthority;

public enum Privilege implements GrantedAuthority {
    VIEW_DEFINITIONS,
    EDIT_REALMS,
    EDIT_USERS,
    EDIT_API_KEYS;

    @Override
    public String getAuthority() {
        return name();
    }
}
