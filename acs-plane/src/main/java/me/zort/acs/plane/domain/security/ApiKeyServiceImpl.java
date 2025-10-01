package me.zort.acs.plane.domain.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.data.security.service.ApiKeyPersistenceService;
import me.zort.acs.plane.api.domain.security.*;
import me.zort.acs.plane.http.util.JwtUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ApiKeyServiceImpl implements ApiKeyService {
    private final ApiKeyPersistenceService persistenceService;
    private final SecretKeyGenerator secretKeyGenerator;
    private final SecretKeyEncoder secretKeyEncoder;

    @Override
    public String createApiKey(CreateApiKeyParams params) {
        SecretKey secret = secretKeyGenerator.generateSecretKey();
        String secretEncoded = secretKeyEncoder.encode(secret);

        ApiKey apiKey = new ApiKeyImpl(-1, params.getName(), params.getPrivileges(), secretEncoded);
        apiKey = persistenceService.saveApiKey(apiKey);

        return buildApiKeyValue(apiKey.getId(), apiKey.getClaims(), secret);
    }

    @Override
    public boolean deleteApiKey(int id) {
        return persistenceService.deleteApiKey(id);
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
                .signWith(secret, secretKeyGenerator.getSignatureAlgorithm())
                .compact();
    }

    @Override
    public Optional<ApiKey> verifyApiKey(String apiKeyValue) {
        ApiKey apiKey;
        try {
            apiKey = decodeApiKey(apiKeyValue);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        return Optional.of(apiKey);
    }

    /**
     * Decode and parse API key from the JWT encoded string.
     *
     * @param apiKeyValue The JWT encoded string
     * @return The api key
     * @throws IllegalArgumentException If the JWT key is invalid
     */
    private ApiKey decodeApiKey(String apiKeyValue) throws IllegalArgumentException {
        String idString = JwtUtils.extractSubject(apiKeyValue);

        int id;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid subject ID");
        }

        ApiKey apiKey = persistenceService.getApiKey(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid API Key"));
        try {
            Jwts.parser()
                    .verifyWith(secretKeyEncoder.decode(apiKey.getSecret()))
                    .build()
                    .parse(apiKeyValue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid API Key", e);
        }
        return apiKey;
    }

    @Override
    public List<ApiKey> getApiKeys() {
        return persistenceService.getAllApiKeys();
    }
}
