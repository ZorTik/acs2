package me.zort.acs.plane.data.security.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.data.security.service.ApiKeyPersistenceService;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import me.zort.acs.plane.data.security.repository.MongoApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApiKeyPersistenceServiceImpl implements ApiKeyPersistenceService {
    private final MongoApiKeyRepository repository;
    private final ApiKeyMapper mapper;

    @Override
    public ApiKey saveApiKey(ApiKey apiKey) {
        ApiKeyDocument document = mapper.toDocument(apiKey);
        document = repository.save(document);

        return mapper.toApiKey(document);
    }

    @Override
    public boolean deleteApiKey(int id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    public Optional<ApiKey> getApiKey(int id) {
        return repository.findById(id).map(mapper::toApiKey);
    }

    @Override
    public List<ApiKey> getAllApiKeys() {
        return repository.findAll()
                .stream()
                .map(mapper::toApiKey)
                .toList();
    }
}
