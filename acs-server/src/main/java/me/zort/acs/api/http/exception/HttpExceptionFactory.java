package me.zort.acs.api.http.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract factory for creating HTTP-related exceptions.
 * <p>
 * Implement this class to provide custom logic for constructing
 * {@link RuntimeException} instances based on specific {@link HttpException} types.
 * </p>
 *
 * <p>
 * This factory allows centralized creation of exceptions for HTTP error handling,
 * enabling consistent exception management across the application.
 * </p>
 *
 * @author ZorTik
 */
public abstract class HttpExceptionFactory {

    /**
     * Creates a {@link RuntimeException} for the given {@link HttpException} type.
     *
     * @param type  the type of HTTP exception
     * @param cause the underlying cause, may be {@code null}
     * @param args  additional arguments for exception message formatting, may be {@code null}
     * @return a new {@link RuntimeException} instance
     */
    @NotNull
    public abstract RuntimeException createException(
            HttpException type, @Nullable Throwable cause, @Nullable Object... args);

    /**
     * Creates a {@link RuntimeException} indicating that the provided query parameters
     * are not applicable for the current controller method.
     *
     * @param options the possible query parameter combinations
     * @return a new {@link RuntimeException} instance for a bad query
     */
    public final RuntimeException createBadQueryException(String... options) {
        // The possible combinations of query params
        String optionsString = String.join(", ", options);

        return createException(HttpException.CONTROLLER_QUERY_NOT_APPLICABLE, null, optionsString);
    }
}
