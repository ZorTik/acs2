package me.zort.acs.client.v1.model.check;

import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.v1.model.AccessRequestWithNodesV1;

import java.util.Set;

public class CheckAccessRequestV1 extends AccessRequestWithNodesV1 {

    public CheckAccessRequestV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed, Set<String> nodes) {
        super(accessor, accessed, nodes);
    }
}
