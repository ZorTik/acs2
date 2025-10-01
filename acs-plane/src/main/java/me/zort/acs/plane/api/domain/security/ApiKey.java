package me.zort.acs.plane.api.domain.security;

import java.util.List;

public interface ApiKey {

    int getId();

    String getName();

    List<Privilege> getClaims();

    String getSecret();
}
