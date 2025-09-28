package me.zort.acs.plane.api.domain.security;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateApiKeyParams {
    private final String name;
    private final List<Privilege> privileges;

}
