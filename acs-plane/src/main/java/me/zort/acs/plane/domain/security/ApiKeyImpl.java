package me.zort.acs.plane.domain.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.api.domain.security.Privilege;

import java.util.List;

@Getter
@AllArgsConstructor
public class ApiKeyImpl implements ApiKey {
    private final int id;
    private final String name;
    private final List<Privilege> claims;

}
