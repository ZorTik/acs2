package me.zort.acs.client.test.http.serializer;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.client.http.HttpResponseImpl;
import me.zort.acs.client.http.serializer.GsonHttpSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GsonHttpSerializerTests {
    private GsonHttpSerializer serializer;

    @AllArgsConstructor
    @NoArgsConstructor
    private static class TestDto {
        private String name;
        private String description;

    }

    @BeforeEach
    public void setUp() {
        serializer = new GsonHttpSerializer(new Gson());
    }

    @Test
    public void testSerializeRequestBody() {
        TestDto dto = new TestDto("Test", "Test description");

        String json = serializer.serializeRequestBody(dto);

        assertEquals("{\"name\":\"Test\",\"description\":\"Test description\"}", json);
    }

    @Test
    public void testDeserializeRequestBody() {
        String json = "{\"name\":\"Test\",\"description\":\"Test description\"}";

        TestDto dto = serializer.deserializeResponseBody(new HttpResponseImpl(200, json), TestDto.class);

        assertEquals("Test", dto.name);
        assertEquals("Test description", dto.description);
    }
}
