package me.zort.acs.client.v1.model.nodes.granted;

import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.v1.model.AccessRequestV1;

public class GrantedNodesRequestV1 extends AccessRequestV1 {

    public GrantedNodesRequestV1(AcsSubjectResolvable accessor, AcsSubjectResolvable accessed) {
        super(accessor, accessed);
    }
}
