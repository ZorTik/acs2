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

    @ApiStatus.OverrideOnly
    protected abstract AcsClient doBuild();

    public @NotNull AcsClientBuilder withOkHttpAdapter() {
        this.httpAdapter = new OkHttpHttpAdapter(new okhttp3.OkHttpClient.Builder()
                .build());

        return this;
    }

    public @NotNull AcsClientBuilder withBaseUrl(final @NotNull String baseUrl) {
        this.baseUrl = Objects.requireNonNull(baseUrl);

        return this;
    }

    public @NotNull AcsClientBuilder withHttpAdapter(final @NotNull HttpAdapter httpAdapter) {
        this.httpAdapter = Objects.requireNonNull(httpAdapter);

        return this;
    }

    public @NotNull AcsClientBuilder withResponseMapper(final @NotNull HttpSerializer responseMapper) {
        this.responseMapper = Objects.requireNonNull(responseMapper);

        return this;
    }

    public @NotNull AcsClient build() {
        Objects.requireNonNull(baseUrl, "Base url cannot be null");
        Objects.requireNonNull(httpAdapter, "Http adapter cannot be null");

        return doBuild();
    }
}
