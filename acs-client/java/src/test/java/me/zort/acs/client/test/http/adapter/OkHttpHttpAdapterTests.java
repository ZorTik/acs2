package me.zort.acs.client.test.http.adapter;

import me.zort.acs.client.http.HttpMethod;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.adapter.OkHttpHttpAdapter;
import me.zort.acs.client.test.BaseTestCase;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OkHttpHttpAdapterTests extends BaseTestCase {
    private OkHttpHttpAdapter adapter;

    @BeforeEach
    public void setUp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        adapter = new OkHttpHttpAdapter(client);
    }

    @Test
    public void testReturns200() throws Exception {
        try (MockWebServer server = mockWebServer(new MockResponse()
                .setResponseCode(200)
                .setBody("{}"))) {
            HttpRequest request = requestBuilder(server)
                    .method(HttpMethod.GET)
                    .build();

            HttpResponse response = adapter.perform(request);

            assertEquals(200, response.getCode());
            assertEquals("{}", response.getBody());
        }
    }

    @Test
    public void testReturns404() throws Exception {
        try (MockWebServer server = mockWebServer(new MockResponse()
                .setResponseCode(404)
                .setBody("{}"))) {
            HttpRequest request = requestBuilder(server)
                    .method(HttpMethod.GET)
                    .build();

            HttpResponse response = adapter.perform(request);

            assertEquals(404, response.getCode());
            assertEquals("{}", response.getBody());
        }
    }
}
