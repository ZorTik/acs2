package me.zort.acs.plane.facade.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.plane.api.http.error.HttpError;

@Getter
@AllArgsConstructor
public class Result<T> {
    private final T value;
    private final HttpError error;

    public static <T> Result<T> ok() {
        return new Result<>(null, null);
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> error(HttpError error) {
        return new Result<>(null, error);
    }

    public static <T> Result<T> error(int statusCode, String message) {
        return new Result<>(null, HttpError.of(statusCode, message));
    }

    public boolean isOk() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }

    public T orApiError() {
        if (isError()) {
            throw error;
        }

        return value;
    }
}
