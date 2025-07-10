package me.zort.acs.client.v1.interceptor;

import lombok.RequiredArgsConstructor;
import me.zort.acs.client.http.HttpInterceptor;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.exception.AcsRequestException;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.v1.model.BasicResponseV1;

@RequiredArgsConstructor
public abstract class ErrorResponseInterceptor implements HttpInterceptor {
    private final HttpSerializer httpSerializer;

    public abstract void interceptError(
            HttpRequest request, HttpResponse response, BasicResponseV1 error) throws RuntimeException;

    @Override
    public void afterCall(HttpRequest request, HttpResponse response) throws RuntimeException {
        if (response.getCode() < 400) {
            return;
        }

        BasicResponseV1 error;
        try {
            error = httpSerializer.deserializeResponseBody(response, BasicResponseV1.class);
        } catch (Exception e) {
            // Workaround
            throw new AcsRequestException(request, BasicResponseV1.class, e);
        }

        interceptError(request, response, error);
    }
}
