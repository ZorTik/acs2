package me.zort.acs.client;

import lombok.RequiredArgsConstructor;
import me.zort.acs.client.http.exception.AcsRequestException;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.adapter.HttpAdapter;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.serializer.HttpSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractAcsClient implements AcsClient {
    private final String baseUrl;

    private final HttpAdapter httpAdapter;
    private final HttpSerializer serializer;

    protected @NotNull <T> T makeCall(
            final Class<T> responseType,
            final BiConsumer<HttpRequest.HttpRequestBuilder, Function<Object, String>> requestConfig) {
        HttpRequest.HttpRequestBuilder requestBuilder = HttpRequest.builder().baseUrl(baseUrl);

        requestConfig.accept(requestBuilder, serializer::serializeRequestBody);

        HttpRequest request = requestBuilder.build();
        try {
            HttpResponse response = httpAdapter.perform(request);

            return serializer.deserializeResponseBody(response, responseType);
        } catch (Exception e) {
            throw new AcsRequestException(request, responseType, e);
        }
    }
}
