package me.zort.acs.domain.provider.options;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.core.model.Node;
import me.zort.acs.core.model.Subject;
import me.zort.acs.core.model.SubjectType;

import java.util.Set;

@Builder
@Getter
public class GroupOptions {
    @Builder.Default
    private final SubjectType subjectType = null;
    @Builder.Default
    private final Subject subject = null;
    private final String name;
    @Builder.Default
    private final Group parentGroup = null;
    private final Set<Node> nodes;

}
