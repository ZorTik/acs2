package me.zort.acs.client.http;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
@Builder
public class HttpRequest {
    private String baseUrl;
    @Builder.Default
    private HttpMethod method = HttpMethod.GET;
    private String path;
    @Builder.Default
    private String body = null;
    @Builder.Default
    private String contentType = "application/json";
    @Builder.Default
    private Map<String, String> queryAttributes = Map.of();

    public void validate() {
        Objects.requireNonNull(baseUrl, "Base url can't be null");
        Objects.requireNonNull(method, "Method can't be null");
        Objects.requireNonNull(path, "Path can't be null");

        if (body != null && !method.supportsBody()) {
            throw new IllegalArgumentException("Method " + method + " does not support body");
        }
    }

    public String buildUrl() {
        return baseUrl + path;
    }

    public boolean hasBody() {
        return body != null;
    }
}
