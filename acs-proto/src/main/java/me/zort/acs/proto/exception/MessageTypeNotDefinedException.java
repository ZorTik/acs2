package me.zort.acs.proto.exception;

import org.jetbrains.annotations.NotNull;

public class MessageTypeNotDefinedException extends RuntimeException {

    public MessageTypeNotDefinedException(@NotNull Class<?> requestedTypeClass) {
        super(String.format("Object of class type %s does not have its definition!", requestedTypeClass.getName()));
    }
}
