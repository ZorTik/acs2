package me.zort.acs.plane.http.error;

public enum ErrorType {

    MALFORMED_RULESET("Malformed rule set: %s");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
