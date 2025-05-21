package me.zort.acs.client.v1.interceptor;

import lombok.RequiredArgsConstructor;
import me.zort.acs.client.http.HttpInterceptor;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.exception.AcsRequestException;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.v1.model.BasicErrorModel;

@RequiredArgsConstructor
public abstract class ErrorResponseInterceptor implements HttpInterceptor {
    private final HttpSerializer httpSerializer;

    public abstract void interceptError(
            HttpRequest request, HttpResponse response, BasicErrorModel error) throws RuntimeException;

    @Override
    public void intercept(HttpRequest request, HttpResponse response) throws RuntimeException {
        if (response.getCode() < 400) {
            return;
        }

        BasicErrorModel error;
        try {
            error = httpSerializer.deserializeResponseBody(response, BasicErrorModel.class);
        } catch (Exception e) {
            // Workaround
            throw new AcsRequestException(request, BasicErrorModel.class, e);
        }

        interceptError(request, response, error);
    }
}
