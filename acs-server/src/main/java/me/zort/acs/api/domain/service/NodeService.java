package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.Node;

import java.util.Optional;

public interface NodeService {

    Node createNode(String value);

    Optional<Node> getNode(String value);

    boolean existsNode(String value);
}
