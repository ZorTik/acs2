package me.zort.acs.http.exception;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.http.exception.HttpExceptionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class HttpExceptionProviderImpl implements HttpExceptionProvider {
    private final MessageSource messageSource;

    @Override
    public @NotNull RuntimeException getException(HttpException type, @Nullable Throwable cause, Object... args) {
        if (args.length != type.getRequiredArgumentCount()) {
            throw new IllegalArgumentException("Invalid number of arguments provided for exception type: " + type);
        }

        String message = messageSource.getMessage(type.getMessage(), args, Locale.getDefault());
        if (cause != null) {
            message = message + " (" + cause.getMessage() + ")";
        }
        return new ACSHttpException(message, type.getStatusCode(), type.getErrorCode());
    }
}
