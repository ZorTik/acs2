package me.zort.acs.plane.facade.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.plane.api.http.error.HttpError;

import java.util.function.Function;

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

    public String getErrorMessage() {
        return isError() ? error.getMessage() : null;
    }

    public Result<T> or(T defaultValue) {
        if (isError()) {
            return Result.ok(defaultValue);
        }

        return this;
    }

    public <M> Result<M> map(Function<T, M> mapper) {
        if (isError()) {
            return Result.error(error);
        }

        return Result.ok(mapper.apply(value));
    }

    public <M> Result<M> flatMap(Function<T, Result<M>> mapper) {
        if (isError()) {
            return Result.error(error);
        }

        return mapper.apply(value);
    }

    public T orError() {
        if (isError()) {
            throw error;
        }

        return value;
    }

    public T orError(Function<HttpError, ? extends HttpError> errorMapper) {
        if (isError()) {
            throw errorMapper.apply(error);
        }

        return value;
    }
}
