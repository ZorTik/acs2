package me.zort.acs.client.http;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HttpResponse {

    int getCode();

    @Nullable
    String getBody();

    static @NotNull HttpResponse ok(@Nullable final String value) {
        return new HttpResponseImpl(200, value);
    }

    static @NotNull HttpResponse of(final int code, @Nullable final String value) {
        return new HttpResponseImpl(code, value);
    }
}
