package me.zort.acs.domain.group.operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.group.operation.AssignNodesOperation;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;

import java.util.Collection;

@RequiredArgsConstructor
public class AssignNodesOperationImpl implements AssignNodesOperation {
    @Getter
    private final Collection<Node> nodes;

    @Override
    public void execute(Group group) throws RuntimeException {
        // TODO
    }
}
