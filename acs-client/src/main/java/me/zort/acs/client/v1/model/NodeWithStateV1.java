package me.zort.acs.client.v1.model;

import lombok.Getter;
import me.zort.acs.client.AcsNodeWithStateResolvable;

@Getter
public class NodeWithStateV1 implements AcsNodeWithStateResolvable {
    private String value;
    private boolean state;

    @Override
    public boolean getState() {
        return state;
    }
}
