package me.zort.acs.http.exception;

import lombok.Getter;

@Getter
public class ACSHttpException extends RuntimeException {
    private final int code;
    private final int errorCode;

    public ACSHttpException(String message, int code, int errorCode) {
        super(message);
        this.code = code;
        this.errorCode = errorCode;
    }

    public boolean isErrorSpecific() {
        return errorCode != 0;
    }
}
