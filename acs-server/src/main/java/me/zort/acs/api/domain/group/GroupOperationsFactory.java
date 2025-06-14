package me.zort.acs.api.domain.group;

import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;

import java.util.Collection;

public interface GroupOperationsFactory {

    Operation<Group> assignParent(Group parent);

    Operation<Group> assignNodes(Collection<Node> nodes);
}
