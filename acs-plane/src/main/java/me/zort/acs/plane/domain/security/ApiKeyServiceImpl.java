package me.zort.acs.plane.domain.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.data.security.service.ApiKeyPersistenceService;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.api.domain.security.ApiKeyService;
import me.zort.acs.plane.api.domain.security.CreateApiKeyParams;
import me.zort.acs.plane.api.domain.security.Privilege;
import me.zort.acs.plane.data.security.model.ApiKeyModel;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ApiKeyServiceImpl implements ApiKeyService {
    private final ApiKeyPersistenceService persistenceService;
    private final DomainModelMapper<ApiKey, ApiKeyModel> mapper;

    @Override
    public String createApiKey(CreateApiKeyParams params) {
        int id = persistenceService.getNextFreeId();

        ApiKeyModel model = mapper.toPersistence(new ApiKeyImpl(id, params.getName(), params.getPrivileges()));
        model = persistenceService.saveApiKey(model);

        Key key = Keys.hmacShaKeyFor(model.getSecret().getBytes());
        return buildApiKeyValue(id, params.getPrivileges(), key);
    }

    /**
     * Builds API key as JWT with defined secret.
     *
     * @param id The id of the key
     * @param claims The claims
     * @param secret The secret
     * @return The JWT encoded string
     */
    private String buildApiKeyValue(int id, List<Privilege> claims, Key secret) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("privileges", claims
                        .stream()
                        .map(Privilege::name).toList())
                .issuedAt(new Date())
                .signWith(secret)
                .compact();
    }

    @Override
    public Optional<ApiKey> verifyApiKey(String apiKey) {
        int id;
        try {
            id = decodeApiKeyId(apiKey);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        return persistenceService.getApiKey(id).map(mapper::toDomain);
    }

    /**
     * Decode and parse API key ID from the JWT encoded string.
     *
     * @param apiKeyValue The JWT encoded string
     * @return The ID of the api key
     * @throws IllegalArgumentException If the JWT key is invalid
     */
    private int decodeApiKeyId(String apiKeyValue) throws IllegalArgumentException {
        Claims claims = Jwts.parser()
                .build()
                .parseUnsecuredClaims(apiKeyValue)
                .getPayload();

        int id;
        try {
            id = Integer.parseInt(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid subject ID");
        }

        ApiKeyModel model = persistenceService.getApiKey(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid API Key"));
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(model.getSecret().getBytes()))
                    .build()
                    .parse(apiKeyValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid API Key", e);
        }
        return id;
    }

    @Override
    public List<ApiKey> getApiKeys() {
        return persistenceService.getAllApiKeys()
                .stream()
                .map(mapper::toDomain)
                .sorted(Comparator.comparingInt(ApiKey::getId).reversed())
                .toList();
    }
}
