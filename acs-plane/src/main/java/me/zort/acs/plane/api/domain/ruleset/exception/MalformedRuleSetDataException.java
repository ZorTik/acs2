package me.zort.acs.plane.api.domain.ruleset.exception;

public class MalformedRuleSetDataException extends RuntimeException {

    public MalformedRuleSetDataException() {
    }

    public MalformedRuleSetDataException(String message) {
        super(message);
    }

    public MalformedRuleSetDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
