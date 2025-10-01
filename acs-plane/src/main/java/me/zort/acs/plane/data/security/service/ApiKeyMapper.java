package me.zort.acs.plane.data.security.service;

import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.api.domain.security.Privilege;
import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import me.zort.acs.plane.domain.security.ApiKeyImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiKeyMapper {

    /**
     * Convert api key domain object to mongo document.
     *
     * @param apiKey The api key
     * @return The mongo document
     */
    public ApiKeyDocument toDocument(ApiKey apiKey) {
        List<String> claims = apiKey.getClaims()
                .stream()
                .map(Privilege::getAuthority)
                .toList();

        return new ApiKeyDocument(apiKey.getId(), apiKey.getName(), apiKey.getSecret(), claims);
    }

    /**
     * Convert api key mongo document to domain object.
     *
     * @param document The mongo document
     * @return The domain object
     */
    public ApiKey toApiKey(ApiKeyDocument document) {
        List<Privilege> claims = document.getClaims()
                .stream()
                .map(Privilege::valueOf)
                .toList();

        return new ApiKeyImpl(document.getId(), document.getName(), claims, document.getSecret());
    }
}
