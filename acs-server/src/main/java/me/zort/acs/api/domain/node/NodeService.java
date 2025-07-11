package me.zort.acs.api.domain.node;

import me.zort.acs.api.domain.node.exception.NodeAlreadyExistsException;
import me.zort.acs.domain.model.Node;

import java.util.Optional;

public interface NodeService {

    Node createNode(String value) throws NodeAlreadyExistsException;

    Optional<Node> getNode(String value);

    boolean existsNode(String value);

    default Node getOrCreateNode(String value) {
        return getNode(value).orElseGet(() -> createNode(value));
    }
}
