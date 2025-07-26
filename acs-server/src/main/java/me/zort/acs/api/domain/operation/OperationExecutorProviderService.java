package me.zort.acs.api.domain.operation;

import me.zort.acs.api.domain.group.Group;

public interface OperationExecutorProviderService {

    <T extends Group> OperationExecutor<T> getExecutorForGroup(Group group);
}
