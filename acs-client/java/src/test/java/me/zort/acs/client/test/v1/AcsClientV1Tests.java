package me.zort.acs.client.test.v1;

import com.google.gson.Gson;
import me.zort.acs.client.http.HttpMethod;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.adapter.OkHttpHttpAdapter;
import me.zort.acs.client.http.model.nodes.list.ListNodesResponse;
import me.zort.acs.client.http.serializer.GsonHttpSerializer;
import me.zort.acs.client.test.BaseTestCase;
import me.zort.acs.client.v1.AcsClientV1;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AcsClientV1Tests extends BaseTestCase {
    private MockWebServer server;
    private AcsClientV1 client;

    private HttpRequest lastRequest = null;
    private Object lastRequestBody = null;

    private class TestHttpSerializer extends GsonHttpSerializer {

        public TestHttpSerializer(Gson gson) {
            super(gson);
        }

        @Override
        public String serializeRequestBody(Object body) {
            lastRequestBody = body;
            return super.serializeRequestBody(body);
        }
    }

    private class TestHttpAdapter extends OkHttpHttpAdapter {

        public TestHttpAdapter(OkHttpClient client) {
            super(client);
        }

        @Override
        public HttpResponse perform(HttpRequest request) throws Exception {
            lastRequest = request;
            return super.perform(request);
        }
    }

    @BeforeEach
    public void setUp() {
        server = mockWebServer();

        client = new AcsClientV1(
                resolveBaseUrl(server),
                new TestHttpAdapter(new OkHttpClient.Builder().build()),
                new TestHttpSerializer(new Gson()));

        lastRequest = null;
        lastRequestBody = null;
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (server != null) {
            server.shutdown();
        }
    }

    // TODO: Tests

    @Test
    public void testListNodesBySubjectType() {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"nodes\":[" +
                        "{\"value\":\"node1\"}," +
                        "{\"value\":\"node2\"}," +
                        "{\"value\":\"node2.subnode\"}]}"));

        ListNodesResponse response = client.listNodes(client.listNodesQueryBuilder()
                .bySubject("subjectType"));

        assertEquals("application/json", lastRequest.getContentType());
        assertEquals(HttpMethod.GET, lastRequest.getMethod());
        assertTrue(lastRequest.getQueryAttributes().containsKey("subjectType"));
        assertEquals("subjectType", lastRequest.getQueryAttributes().get("subjectType"));
        assertEquals("/v1/nodes", lastRequest.getPath());
        assertNull(lastRequestBody);

        assertNotNull(response);
        assertNotNull(response.getNodes());

        assertEquals(3, response.getNodes().size());
        assertEquals("node1", response.getNodes().get(0).getValue());
        assertEquals("node2", response.getNodes().get(1).getValue());
        assertEquals("node2.subnode", response.getNodes().get(2).getValue());
    }
}
