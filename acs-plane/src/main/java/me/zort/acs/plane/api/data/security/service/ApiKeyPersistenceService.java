package me.zort.acs.plane.api.data.security.service;

import me.zort.acs.plane.data.security.model.ApiKeyModel;

import java.util.List;
import java.util.Optional;

public interface ApiKeyPersistenceService {

    ApiKeyModel saveApiKey(ApiKeyModel apiKey);

    Optional<? extends ApiKeyModel> getApiKey(int id);

    int getNextFreeId();

    List<? extends ApiKeyModel> getAllApiKeys();
}
