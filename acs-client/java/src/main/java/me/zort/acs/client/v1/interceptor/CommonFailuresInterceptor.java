package me.zort.acs.client.v1.interceptor;

import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.v1.model.BasicErrorModel;

public class CommonFailuresInterceptor extends ErrorResponseInterceptor {

    public CommonFailuresInterceptor(HttpSerializer httpSerializer) {
        super(httpSerializer);
    }

    @Override
    public void interceptError(
            HttpRequest request, HttpResponse response, BasicErrorModel error) throws RuntimeException {
        // TODO
        // TODO: Možná předtím přidat identifikátory errorů do api
    }
}
