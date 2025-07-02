package me.zort.acs.plane.api.http.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public interface HttpError {

    int getStatusCode();

    String getMessage();

    static @NotNull HttpError of(int statusCode, String message) {
        return new HttpErrorImpl(statusCode, message);
    }

    @Getter
    @AllArgsConstructor
    class HttpErrorImpl implements HttpError {
        private final int statusCode;
        private final String message;

    }
}
