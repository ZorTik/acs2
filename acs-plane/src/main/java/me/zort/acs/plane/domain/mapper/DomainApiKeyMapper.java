package me.zort.acs.plane.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.data.security.service.ApiKeyPersistenceService;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.api.domain.security.Privilege;
import me.zort.acs.plane.api.domain.security.SecretKeyEncoder;
import me.zort.acs.plane.api.domain.security.SecretKeyGenerator;
import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import me.zort.acs.plane.data.security.model.ApiKeyModel;
import me.zort.acs.plane.domain.security.ApiKeyImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class DomainApiKeyMapper implements DomainModelMapper<ApiKey, ApiKeyModel> {
    private final ApiKeyPersistenceService persistenceService;
    private final SecretKeyGenerator secretKeyGenerator;
    private final SecretKeyEncoder secretKeyEncoder;

    /**
     * Maps model to key.
     *
     * @param persistence The persistence model
     * @return The domain object
     */
    @Override
    public ApiKey toDomain(ApiKeyModel persistence) {
        List<Privilege> claims = persistence.getClaims()
                .stream()
                .map(name -> {
                    try {
                        return Privilege.valueOf(name);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        return new ApiKeyImpl(persistence.getId(), persistence.getName(), claims);
    }

    /**
     * Maps domain key to model.
     * This hereby fetches secret from database if the provided key already exists in
     * the database, or generates new one.
     *
     * @param domain The domain key
     * @return The model
     */
    @Override
    public ApiKeyModel toPersistence(ApiKey domain) {
        String secret = persistenceService.getApiKey(domain.getId())
                .map(ApiKeyModel::getSecret)
                .orElseGet(() -> secretKeyEncoder.encode(secretKeyGenerator.generateSecretKey()));
        List<String> claims = domain.getClaims()
                .stream()
                .map(Privilege::name)
                .toList();

        return new ApiKeyDocument(domain.getId(), domain.getName(), secret, claims);
    }
}
