package me.zort.acs.client.v1.model.nodes.list;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.client.AcsNodeResolvable;
import me.zort.acs.client.v1.model.NodeV1;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ListNodesResponseV1 {
    private List<NodeV1> nodes;

    public List<AcsNodeResolvable> getNodes() {
        return nodes
                .stream()
                .map(node -> (AcsNodeResolvable) node)
                .toList();
    }
}
