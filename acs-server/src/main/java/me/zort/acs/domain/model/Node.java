package me.zort.acs.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsHolder;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class Node implements RightsHolder {
    @Getter
    private final String value;

    public boolean isParentOf(Node node, String delimiter) {
        String[] parentParts = this.value.split(Pattern.quote(delimiter));
        String[] childParts = node.getValue().split(Pattern.quote(delimiter));

        if (parentParts.length > childParts.length) {
            return false;
        }

        for (int i = 0; i < parentParts.length; i++) {
            if (parentParts[i].equals("*")) {
                continue;
            }
            if (!parentParts[i].equals(childParts[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<Node> getGrantedNodes() {
        return Set.of(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node node)) return false;
        return Objects.equals(getValue(), node.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
