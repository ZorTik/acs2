package me.zort.acs.domain.group.operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GroupRepository;
import me.zort.acs.api.domain.group.operation.AssignParentOperation;
import me.zort.acs.api.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.domain.model.Group;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Scope("prototype")
@Component
public class AssignParentOperationImpl implements AssignParentOperation {
    private final GroupRepository groupRepository;
    private final PersistenceToDomainMapper<GroupEntity, Group> groupMapper;
    @Getter
    private Group parent;

    public @NotNull AssignParentOperationImpl withParent(Group parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public void execute(Group group) {
        // TODO: Validate they can't be the same group, or different subject types, etc.

        // TODO: Execute the operation
    }
}
