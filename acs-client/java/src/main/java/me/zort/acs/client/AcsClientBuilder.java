package me.zort.acs.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import me.zort.acs.client.http.adapter.HttpAdapter;
import me.zort.acs.client.http.adapter.OkHttpHttpAdapter;
import me.zort.acs.client.http.serializer.GsonHttpSerializer;
import me.zort.acs.client.http.serializer.HttpSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Abstract builder for creating {@link AcsClient} instances.
 * <p></p>
 * Allows configuring essential components such as the base URL,
 * HTTP adapter, and response serializer.
 * Subclasses must implement the {@link #doBuild()} method to provide
 * a concrete client instance.
 */
@Getter(AccessLevel.PROTECTED)
public abstract class AcsClientBuilder {
    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .create();

    private String baseUrl;

    private HttpAdapter httpAdapter;
    private HttpSerializer responseMapper;

    public AcsClientBuilder() {
        this.baseUrl = null;
        this.httpAdapter = null;
        this.responseMapper = new GsonHttpSerializer(DEFAULT_GSON);
    }

    /**
     * Subclasses must override this method to create
     * and return the actual {@link AcsClient} implementation.
     *
     * @return a built {@link AcsClient} instance
     */
    @ApiStatus.OverrideOnly
    protected abstract AcsClient doBuild();

    /**
     * Configures the HTTP adapter to use an OkHttp-based implementation.
     *
     * @return this builder instance for chaining
     */
    public @NotNull AcsClientBuilder withOkHttpAdapter() {
        this.httpAdapter = new OkHttpHttpAdapter(new okhttp3.OkHttpClient.Builder()
                .build());

        return this;
    }

    /**
     * Sets the base URL that the client will use for requests.
     *
     * @param baseUrl the base URL, must not be null
     * @return this builder instance for chaining
     */
    public @NotNull AcsClientBuilder withBaseUrl(final @NotNull String baseUrl) {
        this.baseUrl = Objects.requireNonNull(baseUrl);

        return this;
    }

    /**
     * Sets a custom HTTP adapter for executing requests.
     *
     * @param httpAdapter the HTTP adapter, must not be null
     * @return this builder instance for chaining
     */
    public @NotNull AcsClientBuilder withHttpAdapter(final @NotNull HttpAdapter httpAdapter) {
        this.httpAdapter = Objects.requireNonNull(httpAdapter);

        return this;
    }

    /**
     * Sets a custom serializer to be used for deserializing responses.
     *
     * @param responseMapper the response serializer, must not be null
     * @return this builder instance for chaining
     */
    public @NotNull AcsClientBuilder withHttpSerializer(final @NotNull HttpSerializer responseMapper) {
        this.responseMapper = Objects.requireNonNull(responseMapper);

        return this;
    }

    /**
     * Builds and returns the configured {@link AcsClient} instance.
     * <p></p>
     * Validates that the base URL and HTTP adapter have been configured.
     *
     * @return a new {@link AcsClient} instance
     * @throws NullPointerException if required configuration is missing
     */
    public @NotNull AcsClient build() {
        Objects.requireNonNull(baseUrl, "Base url cannot be null");
        Objects.requireNonNull(httpAdapter, "Http adapter cannot be null");

        return doBuild();
    }
}
