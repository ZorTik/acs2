package me.zort.acs.plane.data.security.service;

import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.api.domain.security.Privilege;
import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import me.zort.acs.plane.domain.security.ApiKeyImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiKeyMapper {

    public ApiKeyDocument toDocument(ApiKey apiKey) {
        List<String> claims = apiKey.getClaims()
                .stream()
                .map(Privilege::getAuthority)
                .toList();

        return new ApiKeyDocument(apiKey.getId(), apiKey.getName(), apiKey.getSecret(), claims);
    }

    public ApiKey toApiKey(ApiKeyDocument document) {
        List<Privilege> claims = document.getClaims()
                .stream()
                .map(Privilege::valueOf)
                .toList();

        return new ApiKeyImpl(document.getId(), document.getName(), claims, document.getSecret());
    }
}
