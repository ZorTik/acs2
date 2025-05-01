package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.service.NodeService;
import me.zort.acs.http.exception.ACSHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HttpNodeMapper {
    private final NodeService nodeService;

    public Node getNode(String node) {
        return nodeService.getNode(node).orElseThrow(() -> new ACSHttpException("No applicable node for " + node, 400));
    }
}
