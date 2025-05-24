package me.zort.acs.api.http.exception;

import me.zort.acs.http.exception.HttpException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HttpExceptionProvider {

    RuntimeException getException(HttpException type);

    @NotNull
    RuntimeException getException(HttpException type, @Nullable Throwable cause, @Nullable Object... args);
}
