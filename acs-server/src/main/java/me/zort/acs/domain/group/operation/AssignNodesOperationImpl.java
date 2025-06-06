package me.zort.acs.domain.group.operation;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.grant.RightsHolderAdapter;
import me.zort.acs.api.domain.group.operation.AssignNodesOperation;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Scope("prototype")
@Component
public class AssignNodesOperationImpl implements AssignNodesOperation {
    private final RightsHolderAdapter rightsHolderAdapter;

    private Collection<Node> nodes;

    public @NotNull AssignNodesOperationImpl withNodes(Collection<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    @Override
    public void execute(Group group) throws RuntimeException {
        if (nodes
                .stream()
                .anyMatch(node -> !rightsHolderAdapter.isPresentInSubjectType(group.getSubjectType(), node))) {
            throw new IllegalArgumentException("One or more nodes are not present in the subject type of the group.");
        }

        nodes.forEach(group::addNode);
    }

    public Collection<Node> getNodes() {
        return List.copyOf(nodes);
    }

    @Override
    public boolean isAutoCommit() {
        return true;
    }
}
