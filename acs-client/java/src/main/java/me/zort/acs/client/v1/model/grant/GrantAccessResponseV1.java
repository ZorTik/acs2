package me.zort.acs.client.v1.model.grant;

import me.zort.acs.client.http.model.grant.GrantAccessResponse;
import me.zort.acs.client.v1.model.NodeStatesResponseV1;

import java.util.Map;

public class GrantAccessResponseV1 extends NodeStatesResponseV1 implements GrantAccessResponse {

    public GrantAccessResponseV1(Map<String, Boolean> states) {
        super(states);
    }
}
