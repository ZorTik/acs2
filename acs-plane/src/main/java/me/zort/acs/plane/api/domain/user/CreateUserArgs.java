package me.zort.acs.plane.api.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserArgs {
    private final String displayName;

}
