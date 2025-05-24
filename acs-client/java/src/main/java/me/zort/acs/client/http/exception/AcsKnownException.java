package me.zort.acs.client.http.exception;

import lombok.Getter;

@Getter
public class AcsKnownException extends RuntimeException {
    private final int errorCode;

    public AcsKnownException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
