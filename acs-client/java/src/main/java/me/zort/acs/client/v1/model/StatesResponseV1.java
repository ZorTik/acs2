package me.zort.acs.client.v1.model;

import lombok.AllArgsConstructor;
import me.zort.acs.client.http.model.StatesResponse;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
public class StatesResponseV1 implements StatesResponse {
    private final Map<String, Boolean> states;

    public boolean anyOf(String... keys) {
        return Arrays.stream(keys).anyMatch(this::checkGrantState);
    }

    @Override
    public boolean allOf(String... keys) {
        return Arrays.stream(keys).allMatch(this::checkGrantState);
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
