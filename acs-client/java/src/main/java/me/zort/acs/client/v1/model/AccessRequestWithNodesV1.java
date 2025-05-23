package me.zort.acs.client.v1.model;

import lombok.Getter;
import me.zort.acs.client.AcsSubjectResolvable;

import java.util.Set;

@Getter
public class AccessRequestWithNodesV1 extends AccessRequestV1 {
    private final Set<String> nodes;

    public AccessRequestWithNodesV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed, Set<String> nodes) {
        super(accessor, accessed);
        this.nodes = nodes;
    }

    public Set<String> getNodes() {
        return Set.copyOf(nodes);
    }
}
