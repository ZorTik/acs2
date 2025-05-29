package me.zort.acs.domain.group;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.api.domain.group.GroupOperationsFactory;
import me.zort.acs.api.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.group.operation.AssignNodesOperationImpl;
import me.zort.acs.domain.group.operation.AssignParentOperationImpl;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GroupOperationsFactoryImpl implements GroupOperationsFactory {
    private final GroupRepository groupRepository;
    private final PersistenceToDomainMapper<GroupEntity, Group> groupMapper;

    @Override
    public Operation<Group> assignParent(Group parent) {
        return new AssignParentOperationImpl(groupRepository, groupMapper, parent);
    }

    @Override
    public Operation<Group> assignNodes(Collection<Node> nodes) {
        return new AssignNodesOperationImpl(nodes);
    }
}
