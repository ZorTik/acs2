package me.zort.acs.client.http.adapter;

import lombok.RequiredArgsConstructor;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import okhttp3.*;

import java.util.Objects;

@RequiredArgsConstructor
public class OkHttpHttpAdapter implements HttpAdapter {
    private final OkHttpClient client;

    @Override
    public HttpResponse perform(HttpRequest request) throws Exception {
        Call call = client.newCall(newRequest(request));

        try (Response response = call.execute()) {
            ResponseBody body = response.body();
            if (body == null) {
                throw new IllegalStateException("Response body is null");
            }

            return HttpResponse.of(response.code(), body.string());
        }
    }

    private Request newRequest(HttpRequest request) {
        String url = request.buildUrl();

        RequestBody body = request.hasBody()
                ? RequestBody.create(
                        Objects.requireNonNull(request.getBody()), MediaType.parse(request.getContentType()))
                : null;
        return new Request.Builder()
                .url(url)
                .method(request.getMethod().name(), body)
                .build();
    }
}
