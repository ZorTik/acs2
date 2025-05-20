package me.zort.acs.client.http.serializer;

import me.zort.acs.client.http.HttpResponse;

public interface HttpSerializer {

    String serializeRequestBody(Object body);

    <T> T deserializeResponseBody(HttpResponse response, Class<T> bodyClass) throws Exception;
}
