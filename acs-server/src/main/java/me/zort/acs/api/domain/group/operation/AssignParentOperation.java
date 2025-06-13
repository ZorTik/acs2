package me.zort.acs.api.domain.group.operation;

import me.zort.acs.api.domain.operation.AutoCommitOperation;
import me.zort.acs.domain.group.Group;

public interface AssignParentOperation extends AutoCommitOperation<Group> {

    Group getParent();
}
