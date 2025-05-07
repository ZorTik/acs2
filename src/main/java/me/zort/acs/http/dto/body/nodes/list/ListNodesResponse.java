package me.zort.acs.http.dto.body.nodes.list;


import lombok.AllArgsConstructor;
import me.zort.acs.http.dto.model.node.NodeDto;

import java.util.List;

@AllArgsConstructor
public class ListNodesResponse {
    private List<NodeDto> nodes;

}
