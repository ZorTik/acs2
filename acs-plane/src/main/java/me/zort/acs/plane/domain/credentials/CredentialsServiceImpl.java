package me.zort.acs.plane.domain.credentials;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.plane.api.domain.credentials.Credentials;
import me.zort.acs.plane.api.domain.credentials.CredentialsService;
import me.zort.acs.plane.data.credentials.CredentialsDocument;
import me.zort.acs.plane.data.credentials.CredentialsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {
    private final CredentialsRepository credentialsRepository;
    private final DomainModelMapper<Credentials, CredentialsDocument> credentialsMapper;

    @Override
    public Optional<? extends Credentials> getCredentialsByUserId(long userId) {
        return credentialsRepository.findByUserId(userId).map(credentialsMapper::toDomain);
    }

    @Override
    public Optional<? extends Credentials> getCredentialsByUsername(String username) {
        return credentialsRepository.findByUsername(username).map(credentialsMapper::toDomain);
    }
}
