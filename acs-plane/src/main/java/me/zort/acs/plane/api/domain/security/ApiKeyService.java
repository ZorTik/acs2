package me.zort.acs.plane.api.domain.security;

import java.util.List;
import java.util.Optional;

public interface ApiKeyService {

    /**
     * Create and save API key.
     *
     * @return The api key string
     */
    String createApiKey(CreateApiKeyParams params);

    boolean deleteApiKey(int id);

    /**
     * Verify, decrypt and check existence of the provided api key.
     *
     * @param apiKey The api key string
     * @return Optionally, the api key info if present and valid
     */
    Optional<ApiKey> verifyApiKey(String apiKey);

    List<ApiKey> getApiKeys();
}
