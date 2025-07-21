package me.zort.acs.plane.api.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateWithSimpleLoginArgs {
    @NotBlank
    private final String username;
    @NotBlank
    private final String displayName;
    @NotBlank
    @Size(min = 8, max = 100)
    private final String password;

}
