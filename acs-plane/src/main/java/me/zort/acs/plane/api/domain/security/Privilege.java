package me.zort.acs.plane.api.domain.security;

import org.springframework.security.core.GrantedAuthority;

public enum Privilege implements GrantedAuthority {
    EDIT_REALMS;

    @Override
    public String getAuthority() {
        return name();
    }
}
