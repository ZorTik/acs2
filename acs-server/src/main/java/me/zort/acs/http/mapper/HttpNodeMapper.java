package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.domain.model.Node;
import me.zort.acs.http.dto.model.node.NodeDto;
import me.zort.acs.http.dto.model.node.NodeWithStateDto;
import me.zort.acs.http.exception.ACSHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HttpNodeMapper {
    private final NodeService nodeService;

    public Node toDomain(String node) {
        return nodeService.getNode(node).orElseThrow(() -> new ACSHttpException("No applicable node for " + node, 400));
    }

    public NodeDto toHttp(Node node) {
        String value = node.getValue();

        return new NodeDto(value);
    }

    public NodeWithStateDto toHttpWithState(Node node, boolean state) {
        String value = node.getValue();

        return new NodeWithStateDto(value, state);
    }
}
