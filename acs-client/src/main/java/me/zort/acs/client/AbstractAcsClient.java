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
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractAcsClient implements AcsClient {
    private final String baseUrl;

    private final HttpAdapter httpAdapter;
    private final HttpSerializer serializer;
    private final List<HttpInterceptor> interceptors;

    protected @NotNull <T> T makeCall(
            final Class<T> responseType,
            final BiConsumer<HttpRequest.HttpRequestBuilder, Function<Object, String>> requestConfig,
            final HttpInterceptor... interceptors) {
        HttpRequest.HttpRequestBuilder requestBuilder = HttpRequest.builder().baseUrl(baseUrl);

        requestConfig.accept(requestBuilder, serializer::serializeRequestBody);

        HttpRequest request = requestBuilder.build();
        request.validate();

        HttpResponse response;
        try {
            response = httpAdapter.perform(request);
        } catch (Exception e) {
            throw new AcsRequestException(request, responseType, e);
        }

        Stream.concat(Arrays.stream(interceptors), this.interceptors.stream())
                .forEach(interceptor -> interceptor.intercept(request, response));

        try {
            return serializer.deserializeResponseBody(response, responseType);
        } catch (Exception e) {
            throw new AcsRequestException(request, responseType, e);
        }
    }
}
