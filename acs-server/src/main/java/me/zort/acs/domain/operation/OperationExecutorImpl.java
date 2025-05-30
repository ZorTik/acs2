package me.zort.acs.domain.operation;

import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.api.domain.operation.OperationExecutor;
import me.zort.acs.api.domain.operation.OperationValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OperationExecutorImpl implements OperationExecutor {
    private final List<OperationValidator<?>> validators;

    public OperationExecutorImpl(List<OperationValidator<?>> validators) {
        this.validators = validators;
    }

    // Type safety ensured by the OperationValidator(s)
    @SuppressWarnings("unchecked")
    @Override
    public final <O, OP extends Operation<O>> boolean executeOperation(OP operation, O object) {
        try {
            validators
                    .stream()
                    .filter(validator ->
                            validator.getOperationClass().isAssignableFrom(operation.getClass()))
                    .map(validator -> (OperationValidator<OP>) validator)
                    .forEach(validator -> validator.validate(operation));

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
