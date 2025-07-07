package me.zort.acs.plane.api.domain.security;

import java.util.List;

public interface ApiKeyService {

    String generateApiKey();

    boolean isValidApiKey(String apiKey);

    List<String> getApiKeys();

    long countApiKeys();
}
