package me.zort.acs.api.domain.group.operation;

import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.core.model.Node;

import java.util.Collection;

public interface AssignNodesOperation extends Operation<Group> {

    Collection<Node> getNodes();
}
