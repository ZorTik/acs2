package me.zort.acs.plane.data.security.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.data.security.service.ApiKeyPersistenceService;
import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import me.zort.acs.plane.data.security.model.ApiKeyModel;
import me.zort.acs.plane.data.security.repository.MongoApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApiKeyPersistenceServiceImpl implements ApiKeyPersistenceService {
    private final MongoApiKeyRepository repository;

    @Override
    public ApiKeyModel saveApiKey(ApiKeyModel model) {
        model = new ApiKeyDocument(model.getId(), model.getName(), model.getSecret(), model.getClaims());

        return repository.save((ApiKeyDocument) model);
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
    public Optional<? extends ApiKeyModel> getApiKey(int id) {
        return repository.findById(id);
    }

    @Override
    public int getNextFreeId() {
        return repository.findTopByOrderByIdDesc()
                .map(ApiKeyDocument::getId)
                .orElse(0) + 1;
    }

    @Override
    public List<? extends ApiKeyModel> getAllApiKeys() {
        return repository.findAll();
    }
}
