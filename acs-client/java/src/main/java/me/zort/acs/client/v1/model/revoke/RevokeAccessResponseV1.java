package me.zort.acs.client.v1.model.revoke;

import me.zort.acs.client.v1.NodesGroupsStatesResponseV1;

import java.util.Map;

public class RevokeAccessResponseV1 extends NodesGroupsStatesResponseV1 {

    public RevokeAccessResponseV1(Map<String, Boolean> nodes, Map<String, Boolean> groups) {
        super(nodes, groups);
    }
}
