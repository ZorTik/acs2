package me.zort.acs.api.domain.group;

import me.zort.acs.api.domain.group.operation.GroupOperation;
import me.zort.acs.domain.model.Group;

public interface GroupOperationExecutor {

    <O extends GroupOperation> boolean executeOperation(O operation, Group group);
}
