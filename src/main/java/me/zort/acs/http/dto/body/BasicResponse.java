package me.zort.acs.http.dto.body;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BasicResponse {
    private int code;
    private String message;
    private Object data;

    public BasicResponse(String message) {
        this(200, message, null);
    }

    public BasicResponse(String message, Object data) {
        this(200, message, data);
    }

    public BasicResponse(int code, String message) {
        this(code, message, null);
    }
}
