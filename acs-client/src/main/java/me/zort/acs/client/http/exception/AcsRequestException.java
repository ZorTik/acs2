package me.zort.acs.client.http.exception;

import lombok.Getter;
import me.zort.acs.client.http.HttpRequest;

@Getter
public class AcsRequestException extends RuntimeException {
    private final HttpRequest request;
    private final Class<?> responseType;

    public AcsRequestException(HttpRequest request, Class<?> responseType, Throwable cause) {
        super(String.format("Request failed. Request: %s", request.toString()), cause);
        this.request = request;
        this.responseType = responseType;
    }
}
