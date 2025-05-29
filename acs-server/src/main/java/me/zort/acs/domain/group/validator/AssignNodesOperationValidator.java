package me.zort.acs.domain.group.validator;

import me.zort.acs.api.domain.group.operation.AssignNodesOperation;
import me.zort.acs.api.domain.operation.OperationValidator;
import me.zort.acs.domain.group.exception.IllegalOperationException;
import org.springframework.stereotype.Component;

@Component
public class AssignNodesOperationValidator implements OperationValidator<AssignNodesOperation> {

    @Override
    public void validate(AssignNodesOperation operation) throws IllegalOperationException {
        // TODO: Podívat se jestli nepůjde použít logika validace z přidávání grantů pro subject
        // TODO
    }

    @Override
    public Class<AssignNodesOperation> getOperationClass() {
        return AssignNodesOperation.class;
    }
}
