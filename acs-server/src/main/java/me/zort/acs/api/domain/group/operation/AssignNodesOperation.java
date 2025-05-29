package me.zort.acs.api.domain.group.operation;

import me.zort.acs.domain.model.Node;

import java.util.Collection;

public interface AssignNodesOperation extends GroupOperation {

    Collection<Node> getNodes();
}
