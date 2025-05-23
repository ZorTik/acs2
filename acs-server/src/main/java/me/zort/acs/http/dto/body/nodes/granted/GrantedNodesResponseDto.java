package me.zort.acs.http.dto.body.nodes.granted;

import lombok.AllArgsConstructor;
import me.zort.acs.http.dto.model.node.NodeWithStateDto;

import java.util.List;

@AllArgsConstructor
public class GrantedNodesResponseDto {
    private List<NodeWithStateDto> nodes;

}
