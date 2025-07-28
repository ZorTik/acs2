package me.zort.acs.api.domain.operation;

import me.zort.acs.api.domain.group.Group;

public interface OperationExecutorProviderService {

    /**
     * Returns an executor for the given group (by its type).
     *
     * @param group the group to get the executor for
     * @return the executor for the group
     * @param <T> the type of the group
     */
    <T extends Group> OperationExecutor<T> getExecutorForGroup(Group group);
}
