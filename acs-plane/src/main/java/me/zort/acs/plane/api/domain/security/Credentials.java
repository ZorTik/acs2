package me.zort.acs.plane.api.domain.security;

import java.util.UUID;

public interface Credentials {

    UUID getId();

    UUID getUserId();

    String getUsername();

    String getHashedPassword();
}
