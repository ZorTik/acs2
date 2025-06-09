package me.zort.acs.client.v1.model.grant;

import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.v1.model.AccessRequestWithNodesGroupsV1;

import java.util.Set;

public class GrantAccessRequestV1 extends AccessRequestWithNodesGroupsV1 {

    public GrantAccessRequestV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed, Set<String> nodes, Set<String> groups) {
        super(accessor, accessed, nodes, groups);
    }
}
