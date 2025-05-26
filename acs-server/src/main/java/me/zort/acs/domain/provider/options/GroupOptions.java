package me.zort.acs.domain.provider.options;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Set;

@Builder
@Getter
public class GroupOptions {
    private final SubjectType subjectType;
    private final String name;
    @Builder.Default
    private final Group parentGroup = null;
    private final Set<Node> nodes;

}
