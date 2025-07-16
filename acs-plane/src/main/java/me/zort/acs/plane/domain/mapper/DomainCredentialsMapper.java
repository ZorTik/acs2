package me.zort.acs.plane.domain.mapper;

import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.credentials.Credentials;
import me.zort.acs.plane.data.credentials.CredentialsDocument;
import me.zort.acs.plane.domain.credentials.CredentialsImpl;
import org.springframework.stereotype.Component;

@Component
public class DomainCredentialsMapper implements DomainModelMapper<Credentials, CredentialsDocument> {

    @Override
    public Credentials toDomain(CredentialsDocument document) {
        return new CredentialsImpl(
                document.getId(), document.getUserId(), document.getUsername(), document.getPasswordHash());
    }

    @Override
    public CredentialsDocument toPersistence(Credentials domain) {
        CredentialsDocument document = new CredentialsDocument();
        document.setId(domain.getId());
        document.setUserId(domain.getUserId());
        document.setUsername(domain.getUsername());
        document.setPasswordHash(domain.getHashedPassword());

        return document;
    }
}
