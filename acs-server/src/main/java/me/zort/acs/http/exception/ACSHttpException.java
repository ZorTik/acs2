package me.zort.acs.http.exception;

import lombok.Getter;

@Getter
public class ACSHttpException extends RuntimeException {
    // Http mappers: 100-199
    // Domain mappers: 200-299
    public static final int HTTP_MAPPER_SUBJECT_NOT_FOUND = 100;

    private final int code;
    private final int errorCode;

    public ACSHttpException(String message, int code) {
        this(message, code, 0);
    }

    public ACSHttpException(String message, int code, int errorCode) {
        super(message);
        this.code = code;
        this.errorCode = errorCode;
    }

    public boolean isErrorSpecific() {
        return errorCode != 0;
    }
}
