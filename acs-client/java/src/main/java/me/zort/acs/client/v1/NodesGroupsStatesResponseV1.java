package me.zort.acs.client.v1;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.client.v1.model.StatesResponseV1;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class NodesGroupsStatesResponseV1 {
    private Map<String, Boolean> nodes;
    private Map<String, Boolean> groups;

    public StatesResponseV1 nodes() {
        return new StatesResponseV1(nodes);
    }

    public StatesResponseV1 groups() {
        return new StatesResponseV1(groups);
    }
}
