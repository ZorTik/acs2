package me.zort.acs.client.http;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
public class HttpRequest {
    private final String baseUrl;
    private final HttpMethod method;
    private final String path;
    private final String body;
    private final String contentType;

    @Builder
    public HttpRequest(
            final @NotNull String baseUrl,
            final @NotNull HttpMethod method,
            final @NotNull String path, final @Nullable String body, final @NotNull String contentType) {
        Objects.requireNonNull(baseUrl, "Base url can't be null");
        Objects.requireNonNull(method, "Method can't be null");
        Objects.requireNonNull(path, "Path can't be null");

        if (body != null && !method.supportsBody()) {
            throw new IllegalArgumentException("Method " + method + " does not support body");
        }

        this.baseUrl = baseUrl;
        this.method = method;
        this.path = path;
        this.body = body;
        this.contentType = contentType;
    }

    public final String buildUrl() {
        return baseUrl + path;
    }

    public boolean hasBody() {
        return body != null;
    }
}
