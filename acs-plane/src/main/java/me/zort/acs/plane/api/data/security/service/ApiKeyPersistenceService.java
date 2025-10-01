package me.zort.acs.plane.api.data.security.service;

import me.zort.acs.plane.api.domain.security.ApiKey;

import java.util.List;
import java.util.Optional;

public interface ApiKeyPersistenceService {

    ApiKey saveApiKey(ApiKey apiKey);

    boolean deleteApiKey(int id);

    Optional<ApiKey> getApiKey(int id);

    List<ApiKey> getAllApiKeys();
}
