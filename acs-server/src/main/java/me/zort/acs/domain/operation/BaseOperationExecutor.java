package me.zort.acs.domain.operation;

import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.api.domain.operation.OperationExecutor;

@Slf4j
public class BaseOperationExecutor<O> implements OperationExecutor<O> {

    @Override
    public <OP extends Operation<O>> boolean executeOperation(OP operation, O object) {
        try {
            operation.execute(object);
            return true;
        } catch (RuntimeException e) {
            log.error(
                    "Failed to execute operation {} on object {}",
                    operation.getClass().getSimpleName(), object.toString(), e);

            return false;
        }
    }
}
