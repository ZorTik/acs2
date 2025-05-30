package me.zort.acs.api.domain.operation;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class AbstractOperationExecutor<O> implements OperationExecutor<O> {
    private final List<OperationValidator<?>> validators;

    public AbstractOperationExecutor(List<OperationValidator<?>> validators) {
        this.validators = validators;
    }

    // Type safety ensured by the OperationValidator(s)
    @SuppressWarnings("unchecked")
    @Override
    public final <OP extends Operation<O>> boolean executeOperation(OP operation, O object) {
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
