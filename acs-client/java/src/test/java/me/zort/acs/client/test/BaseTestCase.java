package me.zort.acs.client.test;

import me.zort.acs.client.http.HttpRequest;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;

public class BaseTestCase {

    @NotNull
    public final MockWebServer mockWebServer(MockResponse... enqueueResponses) {
        MockWebServer server = new MockWebServer();
        try {
            server.start();

            for (MockResponse response : enqueueResponses) {
                server.enqueue(response);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to start MockWebServer", e);
        }
        return server;
    }

    @NotNull
    public final String resolveBaseUrl(MockWebServer server) {
        HttpUrl url = server.url("/");

        return url.scheme() + "://" + url.host() + ":" + url.port();
    }

    public final String resolvePath(MockWebServer server) {
        HttpUrl url = server.url("/");

        return url.encodedPath();
    }

    @NotNull
    public final HttpRequest.HttpRequestBuilder requestBuilder(MockWebServer server) {
        return HttpRequest.builder()
                .baseUrl(resolveBaseUrl(server))
                .path(resolvePath(server));
    }
}
