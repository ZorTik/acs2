package me.zort.acs.plane.domain.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.security.Credentials;
import me.zort.acs.plane.api.domain.security.CredentialsService;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.data.credentials.CredentialsDocument;
import me.zort.acs.plane.data.credentials.CredentialsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {
    private final CredentialsRepository credentialsRepository;
    private final DomainModelMapper<Credentials, CredentialsDocument> credentialsMapper;

    @Override
    public Credentials assignCredentials(User user, String username, String password) {
        Credentials credentials = new CredentialsImpl(UUID.randomUUID(), user.getId(), username, password);

        CredentialsDocument document = credentialsMapper.toPersistence(credentials);
        document = credentialsRepository.save(document);

        return credentialsMapper.toDomain(document);
    }

    @Override
    public void deleteCredentialsByUser(User user) {
        credentialsRepository.deleteByUserId(user.getId());
    }

    @Override
    public Optional<? extends Credentials> getCredentialsByUser(User user) {
        return credentialsRepository.findByUserId(user.getId()).map(credentialsMapper::toDomain);
    }

    @Override
    public Optional<? extends Credentials> getCredentialsByUsername(String username) {
        return credentialsRepository.findByUsername(username).map(credentialsMapper::toDomain);
    }
}
