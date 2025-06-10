package me.zort.acs.client.test.v1.interceptor;

import com.google.gson.Gson;
import me.zort.acs.client.http.HttpMethod;
import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.exception.AcsKnownException;
import me.zort.acs.client.http.serializer.GsonHttpSerializer;
import me.zort.acs.client.http.serializer.HttpSerializer;
import me.zort.acs.client.v1.interceptor.CommonFailuresInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommonFailuresInterceptorTests {
    private CommonFailuresInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        HttpSerializer serializer = new GsonHttpSerializer(new Gson());
        interceptor = new CommonFailuresInterceptor(serializer);
    }

    private HttpRequest mockRequest() {
        return HttpRequest.builder()
                .method(HttpMethod.POST)
                .path("/")
                .baseUrl("http://localhost:8080")
                .contentType("application/json")
                .queryAttributes(new HashMap<>()).build();
    }

    @Test
    public void testWithoutError() {
        HttpRequest request = mockRequest();
        HttpResponse response = mock(HttpResponse.class);
        when(response.getCode()).thenReturn(200);

        interceptor.afterCall(request, response);
    }

    @Test
    public void testWithKnownError() {
        HttpRequest request = mockRequest();
        HttpResponse response = mock(HttpResponse.class);
        when(response.getCode()).thenReturn(400);
        when(response.getBody()).thenReturn("{\"code\": 400,\"errorCode\": 123,\"message\": \"Known error occurred\"}");

        AcsKnownException error = assertThrows(AcsKnownException.class, () -> interceptor.afterCall(request, response));

        assertEquals(123, error.getErrorCode());
    }
}
