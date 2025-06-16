package me.zort.acs.domain.group.operation;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.group.operation.AssignNodesOperation;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Scope("prototype")
@Component
public class AssignNodesOperationImpl implements AssignNodesOperation {
    private final NodeService nodeService;

    private Collection<Node> nodes;

    public @NotNull AssignNodesOperationImpl withNodes(Collection<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    @Transactional
    @Override
    public void execute(Group group) throws RuntimeException {
        nodes.forEach(node -> nodeService.assignNode(node, group));
    }

    public Collection<Node> getNodes() {
        return List.copyOf(nodes);
    }

    @Override
    public boolean isAutoCommit() {
        return false;
    }
}
