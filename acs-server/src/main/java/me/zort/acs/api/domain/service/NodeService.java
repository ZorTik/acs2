package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Optional;

public interface NodeService {

    Node createNode(String value);

    void assignNode(Node node, SubjectType subjectType);

    Optional<Node> getNode(String value);

    boolean existsNode(String value);
}
