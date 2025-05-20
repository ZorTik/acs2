package me.zort.acs.client.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HttpResponseImpl implements HttpResponse {
    private final int code;
    private final String body;

    public HttpResponseImpl(int code) {
        this(code, null);
    }
}
