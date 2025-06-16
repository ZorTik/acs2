package me.zort.acs.domain.grant;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.grant.GrantProvider;
import me.zort.acs.api.domain.grant.GrantService;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.access.AccessValidatorService;
import me.zort.acs.domain.grant.event.GrantAddEvent;
import me.zort.acs.domain.grant.event.GrantRemoveEvent;
import me.zort.acs.domain.grant.exception.GrantAlreadyExistsException;
import me.zort.acs.domain.grant.exception.InvalidGrantException;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GrantServiceImpl implements GrantService {
    private final GrantRepository grantRepository;
    private final GrantProvider grantProvider;
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;
    private final DomainModelMapper<Grant, GrantEntity> grantMapper;
    private final DomainToPersistenceMapper<Subject, SubjectId> subjectIdMapper;
    private final AccessValidatorService accessValidatorService;
    private final ApplicationEventPublisher eventPublisher;

    @CacheEvict(value = "grants", key = "#result.id")
    @Override
    public @NotNull Grant addGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder) {
        getGrant(accessor, accessed, rightsHolder).ifPresent(grant -> {
            throw new GrantAlreadyExistsException(grant);
        });

        try {
            // Validate if this grant can be created for this
            // combination.
            accessValidatorService.validate(accessor, accessed, rightsHolder);
        } catch (IllegalArgumentException e) {
            // Invalid grant
            throw new InvalidGrantException(accessor, accessed, rightsHolder, e);
        }

        Grant grant = grantProvider.getGrant(GrantOptions.builder()
                .id(UUID.randomUUID())
                .accessor(accessor)
                .accessed(accessed)
                .rightsHolder(rightsHolder).build());

        grantRepository.save(grantMapper.toPersistence(grant));
        eventPublisher.publishEvent(new GrantAddEvent(grant));

        return grant;
    }

    @CacheEvict(value = "grants", key = "#grant.id")
    @Override
    public boolean removeGrant(Grant grant) {
        UUID id = grant.getId();

        if (grantRepository.existsById(id)) {
            grantRepository.deleteById(id);

            // Publish remove event to clear potential resources without any links.
            eventPublisher.publishEvent(new GrantRemoveEvent(grant));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Grant> getGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder) {
        SubjectId accessorId = subjectIdMapper.toPersistence(accessor);
        SubjectId accessedId = subjectIdMapper.toPersistence(accessed);

        return rightsHolderTypeRegistry.castAndCallAdapter(
                        rightsHolder,
                        (holder, type) ->
                                type.findGrantEntityForHolder(holder, accessorId, accessedId))
                .map(grantMapper::toDomain)
                .filter(Grant::isValid);
    }

    @Override
    public List<Grant> getGrants(Subject accessor, Subject accessed) {
        SubjectId accessorId = subjectIdMapper.toPersistence(accessor);
        SubjectId accessedId = subjectIdMapper.toPersistence(accessed);

        return grantRepository.findByAccessor_IdAndAccessed_Id(accessorId, accessedId)
                .stream()
                .map(grantMapper::toDomain)
                .filter(Grant::isValid).toList();
    }

    @Override
    public int getGrantsCount(Subject accessor) {
        return grantRepository.countByAccessor_Id(subjectIdMapper.toPersistence(accessor));
    }
}
