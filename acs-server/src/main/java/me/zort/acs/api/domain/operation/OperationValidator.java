package me.zort.acs.api.domain.operation;

import me.zort.acs.domain.group.exception.IllegalOperationException;

public interface OperationValidator<O extends Operation<?>> {

    void validate(O operation) throws IllegalOperationException;

    Class<O> getOperationClass();
}
