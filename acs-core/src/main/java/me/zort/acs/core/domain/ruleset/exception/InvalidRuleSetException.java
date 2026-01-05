package me.zort.acs.core.domain.ruleset.exception;

public class InvalidRuleSetException extends RuntimeException {

    public InvalidRuleSetException() {
    }

    public InvalidRuleSetException(String message) {
        super(message);
    }

    public InvalidRuleSetException(String message, Throwable cause) {
        super(message, cause);
    }
}
