package me.zort.acs.domain.group;

import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.operation.AutoCommitOperationExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupOperationExecutor extends AutoCommitOperationExecutor<Group, GroupEntity> {

    @Autowired
    public GroupOperationExecutor(
            GroupRepository groupRepository, DomainToPersistenceMapper<Group, GroupEntity> groupMapper) {
        super(groupRepository, groupMapper);
    }
}
