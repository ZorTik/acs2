package me.zort.acs.plane.domain.mapper;

import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.data.security.model.ApiKeyDocument;
import org.springframework.stereotype.Component;

@Component
public class DomainApiKeyMapper implements DomainModelMapper<String, ApiKeyDocument> {

    @Override
    public String toDomain(ApiKeyDocument persistence) {
        return persistence.getKey();
    }

    @Override
    public ApiKeyDocument toPersistence(String domain) {
        ApiKeyDocument document = new ApiKeyDocument();
        document.setKey(domain);

        return document;
    }
}
