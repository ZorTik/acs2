package me.zort.acs.domain.group.validator;

import me.zort.acs.api.domain.group.operation.AssignParentOperation;
import me.zort.acs.api.domain.operation.OperationValidator;
import me.zort.acs.domain.group.exception.IllegalOperationException;
import org.springframework.stereotype.Component;

@Component
public class AssignParentOperationValidator implements OperationValidator<AssignParentOperation> {
    @Override
    public void validate(AssignParentOperation operation) throws IllegalOperationException {
        // TODO: Validate they can't be the same group, or different subject types, etc.
    }

    @Override
    public Class<AssignParentOperation> getOperationClass() {
        return AssignParentOperation.class;
    }
}
