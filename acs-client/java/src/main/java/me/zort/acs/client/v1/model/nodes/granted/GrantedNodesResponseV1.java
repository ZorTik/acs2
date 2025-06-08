package me.zort.acs.client.v1.model.nodes.granted;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.client.AcsNodeWithStateResolvable;
import me.zort.acs.client.v1.model.NodeWithStateV1;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GrantedNodesResponseV1 {
    private List<NodeWithStateV1> nodes;

    public List<AcsNodeWithStateResolvable> getNodes() {
        return nodes
                .stream()
                .map(node -> (AcsNodeWithStateResolvable) node)
                .toList();
    }

    public List<AcsNodeWithStateResolvable> getNodesByState(boolean state) {
        return getNodes()
                .stream()
                .filter(node -> node.getState() == state)
                .toList();
    }
}
