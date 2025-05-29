package me.zort.acs.domain.group.exception;

import lombok.Getter;
import me.zort.acs.api.domain.operation.Operation;
import me.zort.acs.domain.model.Group;

@Getter
public class IllegalOperationException extends RuntimeException {
    private final Operation operation;
    private final Group group;

    public IllegalOperationException(String message, Operation operation, Group group) {
        super(message);
        this.operation = operation;
        this.group = group;
    }
}
