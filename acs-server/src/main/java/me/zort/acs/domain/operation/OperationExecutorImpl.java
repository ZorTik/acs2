package me.zort.acs.domain.operation;

import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.api.domain.operation.OperationExecutor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OperationExecutorImpl implements OperationExecutor {

    @Override
    public final <O, OP extends Operation<O>> boolean executeOperation(OP operation, O object) {
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
