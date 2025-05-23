package me.zort.acs.client.http.serializer;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import me.zort.acs.client.http.HttpResponse;

@AllArgsConstructor
public class GsonHttpSerializer implements HttpSerializer {
    private final Gson gson;

    @Override
    public String serializeRequestBody(Object body) {
        return gson.toJson(body);
    }

    @Override
    public <T> T deserializeResponseBody(HttpResponse response, Class<T> bodyClass) {
        return gson.fromJson(response.getBody(), bodyClass);
    }
}
