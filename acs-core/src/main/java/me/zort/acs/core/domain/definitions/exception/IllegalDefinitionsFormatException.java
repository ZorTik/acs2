package me.zort.acs.core.domain.definitions.exception;

public class IllegalDefinitionsFormatException extends RuntimeException {

    public IllegalDefinitionsFormatException(String message) {
        super(message);
    }

    public IllegalDefinitionsFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
