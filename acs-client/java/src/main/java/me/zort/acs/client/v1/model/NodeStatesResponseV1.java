package me.zort.acs.client.v1.model;

import lombok.AllArgsConstructor;
import me.zort.acs.client.http.model.NodeStatesResponse;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
public class NodeStatesResponseV1 implements NodeStatesResponse {
    private final Map<String, Boolean> states;

    public boolean anyOf(String... nodes) {
        return Arrays.stream(nodes).anyMatch(this::checkGrantState);
    }

    @Override
    public boolean allOf(String... nodes) {
        return Arrays.stream(nodes).allMatch(this::checkGrantState);
    }

    @Override
    public boolean all() {
        return states.keySet().stream().allMatch(this::checkGrantState);
    }

    private boolean checkGrantState(String node) {
        if (states.containsKey(node)) {
            return states.get(node);
        }

        throw new IllegalArgumentException("Node " + node + " is not part of this grant response!");
    }
}
