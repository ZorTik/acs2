package me.zort.acs.domain.provider.options;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.domain.model.Subject;

import java.util.UUID;

@Builder
@Getter
public class GrantOptions {
    @NotNull
    private final UUID id;
    @NotNull
    private final Subject accessor;
    @NotNull
    private final Subject accessed;
    @NotNull
    private final RightsHolder rightsHolder;

}
