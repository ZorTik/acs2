package me.zort.acs.plane.domain.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.security.ApiKeyService;
import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import me.zort.acs.plane.data.security.repository.MongoApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class ApiKeyServiceImpl implements ApiKeyService {
    private final MongoApiKeyRepository apiKeyRepository;
    private final DomainModelMapper<String, ApiKeyDocument> apiKeyMapper;

    @Override
    public String generateApiKey() {
        String apiKey = UUID.randomUUID().toString();

        ApiKeyDocument document = apiKeyMapper.toPersistence(apiKey);
        document = apiKeyRepository.save(document);

        return apiKeyMapper.toDomain(document);
    }

    @Override
    public boolean isValidApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return false;
        }

        return apiKeyRepository.existsById(apiKey);
    }

    @Override
    public List<String> getApiKeys() {
        return apiKeyRepository.findAll()
                .stream()
                .map(apiKeyMapper::toDomain).toList();
    }

    @Override
    public long countApiKeys() {
        return apiKeyRepository.count();
    }
}
