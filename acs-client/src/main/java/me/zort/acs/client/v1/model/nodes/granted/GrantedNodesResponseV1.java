package me.zort.acs.client.v1.model.nodes.granted;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.client.AcsNodeWithStateResolvable;
import me.zort.acs.client.http.model.nodes.granted.GrantedNodesResponse;
import me.zort.acs.client.v1.model.NodeWithStateV1;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GrantedNodesResponseV1 implements GrantedNodesResponse {
    private List<NodeWithStateV1> nodes;

    @Override
    public List<AcsNodeWithStateResolvable> getNodes() {
        return nodes
                .stream()
                .map(node -> (AcsNodeWithStateResolvable) node)
                .toList();
    }

    @Override
    public List<AcsNodeWithStateResolvable> getNodesByState(boolean state) {
        return getNodes()
                .stream()
                .filter(node -> node.getState() == state)
                .toList();
    }
}
