package me.zort.acs.http.dto.body;

import lombok.AllArgsConstructor;

@SuppressWarnings("all")
@AllArgsConstructor
public class BasicResponse {
    private int code;
    private int errorCode;
    private String message;
    private Object data;

    public BasicResponse(String message) {
        this(200, 0, message, null);
    }

    public BasicResponse(String message, Object data) {
        this(200, 0, message, data);
    }

    public BasicResponse(int code, int errorCode, String message) {
        this(code, errorCode, message, null);
    }
}
