package me.zort.acs.plane.api.domain.user.exception;

public class AccountCreateException extends RuntimeException {

    public AccountCreateException(String message) {
        super(message);
    }

    public AccountCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
