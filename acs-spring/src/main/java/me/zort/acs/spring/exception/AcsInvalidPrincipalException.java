package me.zort.acs.spring.exception;

import lombok.Getter;

@Getter
public class AcsInvalidPrincipalException extends RuntimeException {
    private final Object principal;

    public AcsInvalidPrincipalException(final Object principal, String message) {
        super("Invalid principal: " + message);
        this.principal = principal;
    }
}
