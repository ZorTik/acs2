package me.zort.acs.client.v1.model.grant;

import me.zort.acs.client.v1.NodesGroupsStatesResponseV1;

import java.util.Map;

public class GrantAccessResponseV1 extends NodesGroupsStatesResponseV1 {

    public GrantAccessResponseV1(Map<String, Boolean> nodes, Map<String, Boolean> groups) {
        super(nodes, groups);
    }
}
