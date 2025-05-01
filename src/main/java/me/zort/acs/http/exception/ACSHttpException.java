package me.zort.acs.http.exception;

import lombok.Getter;

@Getter
public class ACSHttpException extends RuntimeException {
    private final int code;

    public ACSHttpException(String message, int code) {
        super(message);
        this.code = code;
    }
}
