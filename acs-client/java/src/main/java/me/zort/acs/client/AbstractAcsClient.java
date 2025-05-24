package me.zort.acs.client;

import lombok.RequiredArgsConstructor;
import me.zort.acs.client.http.HttpInterceptor;
import me.zort.acs.client.http.exception.AcsRequestException;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.adapter.HttpAdapter;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.serializer.HttpSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Abstract base implementation of the {@link AcsClient} interface.
 * <p></p>
 * This class provides common HTTP request execution logic, including request building,
 * serialization/deserialization, and interceptor invocation.
 * It delegates the actual HTTP call to the configured {@link HttpAdapter}.
 * <p></p>
 * Key responsibilities:
 * <ul>
 *     <li>Builds and validates HTTP requests</li>
 *     <li>Applies request configuration using a BiConsumer</li>
 *     <li>Executes HTTP calls through the {@link HttpAdapter}</li>
 *     <li>Invokes pre- and post-call {@link HttpInterceptor}s</li>
 *     <li>Serializes request bodies and deserializes response bodies using {@link HttpSerializer}</li>
 *     <li>Handles exceptions and wraps them into {@link AcsRequestException}</li>
 * </ul>
 *
 * Designed to be extended by concrete ACS client implementations
 * that provide specific API interaction methods.
 */
@RequiredArgsConstructor
public abstract class AbstractAcsClient implements AcsClient {
    private final String baseUrl;

    private final HttpAdapter httpAdapter;
    private final HttpSerializer serializer;
    private final List<HttpInterceptor> interceptors;

    protected @NotNull <T> T executeRequest(
            final Class<T> responseType,
            final BiConsumer<HttpRequest.HttpRequestBuilder, Function<Object, String>> requestConfig,
            final HttpInterceptor... interceptors) {
        HttpRequest.HttpRequestBuilder requestBuilder = HttpRequest.builder().baseUrl(baseUrl);

        requestConfig.accept(requestBuilder, serializer::serializeRequestBody);

        HttpRequest request = requestBuilder.build();
        request.validate();

        HttpResponse response = executeAndInterceptRequest(request, responseType, interceptors);
        try {
            return serializer.deserializeResponseBody(response, responseType);
        } catch (Exception e) {
            throw new AcsRequestException(request, responseType, e);
        }
    }

    private @NotNull HttpResponse executeAndInterceptRequest(
            HttpRequest request, Class<?> responseType, HttpInterceptor... interceptors) {
        callInterceptors(interceptor -> interceptor.beforeCall(request), interceptors);
        HttpResponse response;
        try {
            response = httpAdapter.perform(request);
        } catch (Exception e) {
            throw new AcsRequestException(request, responseType, e);
        }
        callInterceptors(interceptor -> interceptor.afterCall(request, response), interceptors);

        return response;
    }

    private void callInterceptors(Consumer<HttpInterceptor> action, HttpInterceptor... additionalInterceptors) {
        Stream.concat(Arrays.stream(additionalInterceptors), this.interceptors.stream()).forEach(action);
    }
}
