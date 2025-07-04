package me.zort.acs.plane.api.http.error;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class HttpError extends RuntimeException {
    private final int statusCode;
    private final String message;

    public HttpError(HttpError error) {
        this(error.getStatusCode(), error.getMessage());
    }

    public HttpError(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public static @NotNull HttpError of(int statusCode, String message) {
        return new HttpError(statusCode, message);
    }
}
