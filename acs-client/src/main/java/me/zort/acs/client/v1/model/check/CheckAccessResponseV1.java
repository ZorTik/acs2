package me.zort.acs.client.v1.model.check;

import lombok.AllArgsConstructor;
import me.zort.acs.client.http.model.check.CheckAccessResponse;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
public class CheckAccessResponseV1 implements CheckAccessResponse {
    private final Map<String, Boolean> states;

    public boolean grants(String... nodes) {
        return Arrays.stream(nodes).anyMatch(this::checkGrantState);
    }

    private boolean checkGrantState(String node) {
        if (states.containsKey(node)) {
            return states.get(node);
        }

        throw new IllegalArgumentException("Node " + node + " is not part of this grant response!");
    }
}
