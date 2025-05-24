package me.zort.acs.api.http.exception;

import me.zort.acs.http.exception.HttpException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HttpExceptionFactory {

    @NotNull
    RuntimeException createException(HttpException type, @Nullable Throwable cause, @Nullable Object... args);
}
