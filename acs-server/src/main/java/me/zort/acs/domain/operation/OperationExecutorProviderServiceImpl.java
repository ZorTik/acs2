package me.zort.acs.domain.operation;

import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.api.domain.operation.OperationExecutorProviderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OperationExecutorProviderServiceImpl implements OperationExecutorProviderService {
    private final OperationExecutor<Group> staticGroupExecutor;
    private final OperationExecutor<Group> dynamicGroupExecutor;

    public OperationExecutorProviderServiceImpl(
            @Qualifier("staticGroupExecutor") OperationExecutor<Group> staticGroupExecutor,
            @Qualifier("dynamicGroupExecutor") OperationExecutor<Group> dynamicGroupExecutor) {
        this.staticGroupExecutor = staticGroupExecutor;
        this.dynamicGroupExecutor = dynamicGroupExecutor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Group> OperationExecutor<T> getExecutorForGroup(Group group) {
        if (group.getSubject() != null) {
            // Dynamic group executor
            return (OperationExecutor<T>) dynamicGroupExecutor;
        } else {
            // Static group executor
            return (OperationExecutor<T>) staticGroupExecutor;
        }
    }
}
