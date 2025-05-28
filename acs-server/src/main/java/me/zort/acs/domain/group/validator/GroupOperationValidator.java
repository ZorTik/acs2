package me.zort.acs.domain.group.validator;

import me.zort.acs.api.domain.group.operation.GroupOperation;
import me.zort.acs.domain.group.exception.IllegalOperationException;

public interface GroupOperationValidator<O extends GroupOperation> {

    void validate(O operation) throws IllegalOperationException;

    Class<O> getOperationClass();
}
