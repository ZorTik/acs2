package me.zort.acs.client.v1.model.check;

import me.zort.acs.client.v1.model.StatesResponseV1;

import java.util.Map;


public class CheckAccessResponseV1 extends StatesResponseV1 {

    public CheckAccessResponseV1(Map<String, Boolean> states) {
        super(states);
    }
}
