package me.zort.acs.domain.group.operation;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.api.domain.group.operation.AssignNodesOperation;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
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
    private final GroupRepository groupRepository;
    private final DomainToPersistenceMapper<Group, GroupEntity> groupMapper;
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;

    private Collection<Node> nodes;

    public @NotNull AssignNodesOperationImpl withNodes(Collection<Node> nodes) {
        this.nodes = nodes;
        return this;
    }

    private boolean isPresentInSubjectType(Node node, SubjectType subjectType) {
        return rightsHolderTypeRegistry
                .castAndCallAdapter(node, (holder, type) ->
                        type.isPresentInSubjectType(holder, subjectType));
    }

    @Transactional
    @Override
    public void execute(Group group) throws RuntimeException {
        try {
            for (Node node : nodes) {
                if (!isPresentInSubjectType(node, group.getSubjectType())) {
                    throw new IllegalArgumentException(String.format(
                            "Node %s is not present in the subject type %s of the group %s.",
                            node.getValue(), group.getSubjectType().getId(), group.getName()));
                }
            }

            if (nodes
                    .stream()
                    .allMatch(group::containsNode)) {
                // If all nodes are already assigned, we can skip the operation.
                return;
            }

            nodes.forEach(group::addNode);

            GroupEntity groupEntity = groupMapper.toPersistence(group);
            groupRepository.save(groupEntity);
        } catch (Exception e) {
            nodes.forEach(group::removeNode);
            throw new RuntimeException(e);
        }
    }

    public Collection<Node> getNodes() {
        return List.copyOf(nodes);
    }

    @Override
    public boolean isAutoCommit() {
        return false;
    }
}
