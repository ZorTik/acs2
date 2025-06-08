package me.zort.acs.client.v1.model.revoke;

import me.zort.acs.client.v1.model.NodeStatesResponseV1;

import java.util.Map;

public class RevokeAccessResponseV1 extends NodeStatesResponseV1 {

    public RevokeAccessResponseV1(Map<String, Boolean> states) {
        super(states);
    }
}
