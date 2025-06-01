package me.zort.acs.domain.provider.options;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

import java.util.UUID;

@Builder
@Getter
public class GrantOptions {
    private final UUID id;
    private final Subject accessor;
    private final Subject accessed;
    private final RightsHolder rightsHolder;

}
