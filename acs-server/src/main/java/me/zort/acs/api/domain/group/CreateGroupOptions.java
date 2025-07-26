package me.zort.acs.api.domain.group;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class CreateGroupOptions {
    @Builder.Default
    private final SubjectType subjectType = null;
    @Builder.Default
    private final Subject subject = null;

    private final String name;

    @Builder.Default
    private final Group parentGroup = null;
    @Builder.Default
    private final List<Node> nodes = new ArrayList<>();

}
