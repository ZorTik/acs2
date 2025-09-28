package me.zort.acs.plane.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.http.dto.model.ListedApiKey;
import me.zort.acs.plane.http.dto.model.ListedPrivilege;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class HttpApiKeyMapper {
    private final HttpPrivilegeMapper privilegeMapper;

    public ListedApiKey toHttpListed(ApiKey apiKey) {
        List<ListedPrivilege> claims = apiKey.getClaims()
                .stream()
                .map(privilegeMapper::toHttpListed)
                .toList();

        return new ListedApiKey(apiKey.getId(), apiKey.getName(), claims);
    }
}
