package me.zort.acs.api.http.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class HttpExceptionFactory {

    @NotNull
    public abstract RuntimeException createException(HttpException type, @Nullable Throwable cause, @Nullable Object... args);

    public final RuntimeException createBadQueryException(String... options) {
        // The possible combinations of query params
        String optionsString = String.join(", ", options);

        return createException(HttpException.CONTROLLER_QUERY_NOT_APPLICABLE, null, optionsString);
    }
}
