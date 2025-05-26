package me.zort.acs.domain.provider.options;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

@Builder
@Getter
public class GrantOptions {
    private final Subject accessor;
    private final Subject accessed;
    private final Node node;

}
