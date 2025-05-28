package me.zort.acs.api.domain.group.operation;

import me.zort.acs.domain.model.Group;

public interface AssignParentOperation extends GroupOperation {

    Group getParent();
}
