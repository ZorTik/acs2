package me.zort.acs.domain.group.exception;

import lombok.Getter;
import me.zort.acs.api.domain.group.operation.GroupOperation;
import me.zort.acs.domain.model.Group;

@Getter
public class IllegalOperationException extends RuntimeException {
    private final GroupOperation operation;
    private final Group group;

    public IllegalOperationException(String message, GroupOperation operation, Group group) {
        super(message);
        this.operation = operation;
        this.group = group;
    }
}
