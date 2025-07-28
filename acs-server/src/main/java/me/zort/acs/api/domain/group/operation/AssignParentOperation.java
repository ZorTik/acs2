package me.zort.acs.api.domain.group.operation;

import me.zort.acs.api.domain.operation.AutoCommittableOperation;
import me.zort.acs.api.domain.group.Group;

public interface AssignParentOperation extends AutoCommittableOperation<Group> {

    Group getParent();
}
