package me.zort.acs.client.v1.interceptor;

import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.exception.AcsKnownException;
import me.zort.acs.client.http.exception.AcsRequestException;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.v1.model.BasicErrorModel;

public class CommonFailuresInterceptor extends ErrorResponseInterceptor {

    public CommonFailuresInterceptor(HttpSerializer httpSerializer) {
        super(httpSerializer);
    }

    @Override
    public void interceptError(
            HttpRequest request, HttpResponse response, BasicErrorModel error) throws RuntimeException {
        if (error.getErrorCode() > 0) {
            throw new AcsKnownException(error.getMessage(), error.getErrorCode());
        } else {
            throw new AcsRequestException(
                    request, BasicErrorModel.class, new RuntimeException("Unexpected error: " + error.getMessage()));
        }
    }
}
