package me.zort.acs.domain.group.operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.group.operation.AssignParentOperation;
import me.zort.acs.api.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.model.Group;

@RequiredArgsConstructor
public class AssignParentOperationImpl implements AssignParentOperation {
    private final GroupRepository groupRepository;
    private final PersistenceToDomainMapper<GroupEntity, Group> groupMapper;
    @Getter
    private final Group parent;

    @Override
    public void execute(Group group) {
        // TODO: Execute the operation
    }
}
