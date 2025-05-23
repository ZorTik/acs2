package me.zort.acs.client.v1.model.check;

import me.zort.acs.client.http.model.check.CheckAccessResponse;
import me.zort.acs.client.v1.model.NodeStatesResponseV1;

import java.util.Map;


public class CheckAccessResponseV1 extends NodeStatesResponseV1 implements CheckAccessResponse {

    public CheckAccessResponseV1(Map<String, Boolean> states) {
        super(states);
    }
}
