package me.zort.acs.api.domain.group.operation;

import me.zort.acs.api.domain.operation.AutoCommitOperation;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.domain.model.Group;

public interface AssignParentOperation extends AutoCommitOperation<Group> {

    Group getParent();
}
