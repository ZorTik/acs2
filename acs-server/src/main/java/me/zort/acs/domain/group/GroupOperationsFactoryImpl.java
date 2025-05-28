package me.zort.acs.domain.group;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.group.operation.GroupOperation;
import me.zort.acs.api.domain.group.GroupOperationsFactory;
import me.zort.acs.api.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.group.operation.AssignParentOperationImpl;
import me.zort.acs.domain.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GroupOperationsFactoryImpl implements GroupOperationsFactory {
    private final GroupRepository groupRepository;
    private final PersistenceToDomainMapper<GroupEntity, Group> groupMapper;

    @Override
    public GroupOperation assignParent(Group parent) {
        return new AssignParentOperationImpl(groupRepository, groupMapper, parent);
    }
}
