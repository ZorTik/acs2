package me.zort.acs.client.v1.model;

import me.zort.acs.client.AcsSubjectResolvable;

import java.util.Set;

public class AccessRequestWithNodesGroupsV1 extends AccessRequestWithNodesV1 {
    private final Set<String> groups;

    public AccessRequestWithNodesGroupsV1(
            AcsSubjectResolvable accessor,
            AcsSubjectResolvable accessed, Set<String> nodes, Set<String> groups) {
        super(accessor, accessed, nodes);
        this.groups = groups;
    }

    public Set<String> getGroups() {
        return Set.copyOf(groups);
    }
}
