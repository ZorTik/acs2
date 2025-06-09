package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.model.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@RequiredArgsConstructor
public class NodesBulk implements RightsHolder {
    private final Set<Node> nodes;

    public static @NotNull NodesBulk of(@NotNull Set<Node> nodes) {
        return new NodesBulk(nodes);
    }

    @Override
    public Set<Node> getGrantedNodes() {
        return Set.copyOf(nodes);
    }
}
