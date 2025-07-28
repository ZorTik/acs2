package me.zort.acs.domain.subjecttype.operation;

import me.zort.acs.api.domain.operation.AutoCommittableOperation;
import me.zort.acs.api.domain.operation.OperationCallContext;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Scope("prototype")
@Component
public class AssignNodesOperation implements AutoCommittableOperation<SubjectType> {
    private Collection<Node> nodes = List.of();

    public @NotNull AssignNodesOperation withNodes(Collection<Node> nodes) {
        this.nodes = nodes;

        return this;
    }

    @Override
    public void execute(SubjectType subjectType, OperationCallContext context) throws RuntimeException {
        Collection<Node> nodes = this.nodes
                .stream()
                .filter(node -> !subjectType.containsNode(node))
                .toList();
        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("No new nodes to assign to the subject type.");
        }

        nodes.forEach(subjectType::addNode);
    }
}
