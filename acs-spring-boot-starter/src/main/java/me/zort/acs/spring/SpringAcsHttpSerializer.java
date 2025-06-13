package me.zort.acs.spring;

import lombok.RequiredArgsConstructor;
import me.zort.acs.client.http.HttpResponse;
import me.zort.acs.client.http.serializer.HttpSerializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Serializer for HTTP requests and responses using Spring's {@link AbstractJsonHttpMessageConverter}.
 * Allows serialization and deserialization of objects without binding to a specific JSON library.
 */
@RequiredArgsConstructor
public class SpringAcsHttpSerializer implements HttpSerializer {
    private final AbstractJsonHttpMessageConverter messageConverter;

    /**
     * Serializes an object to a JSON string using {@link AbstractJsonHttpMessageConverter}.
     *
     * @param body The object to serialize.
     * @return The JSON representation of the object as a {@link String}.
     * @throws RuntimeException If serialization fails.
     */
    @Override
    public String serializeRequestBody(Object body) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpOutputMessage outMessage = new HttpOutputMessage() {
            @Override
            public @NotNull OutputStream getBody() {
                return out;
            }
            @Override
            public @NotNull HttpHeaders getHeaders() {
                return new HttpHeaders();
            }
        };

        try {
            messageConverter.write(body, null, outMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out.toString(StandardCharsets.UTF_8);
    }

    /**
     * Deserializes a JSON response to an object of the specified class using {@link AbstractJsonHttpMessageConverter}.
     *
     * @param response  The HTTP response containing the JSON.
     * @param bodyClass The class to deserialize the JSON into.
     * @param <T>       The type of the resulting object.
     * @return The object deserialized from JSON.
     * @throws Exception If deserialization fails.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserializeResponseBody(HttpResponse response, Class<T> bodyClass) throws Exception {
        byte[] bytes = Objects.requireNonNull(response.getBody()).getBytes("UTF-8");

        HttpInputMessage inMessage = new HttpInputMessage() {
            @Override
            public @NotNull InputStream getBody() {
                return new ByteArrayInputStream(bytes);
            }
            @Override
            public @NotNull HttpHeaders getHeaders() {
                return new HttpHeaders();
            }
        };

        return (T) messageConverter.read(bodyClass, inMessage);
    }
}
