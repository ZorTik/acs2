package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.provider.GrantProvider;
import me.zort.acs.api.domain.service.GrantService;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.access.AccessValidatorService;
import me.zort.acs.domain.event.GrantAddEvent;
import me.zort.acs.domain.event.GrantRemoveEvent;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.options.GrantOptions;
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
    private final DomainModelMapper<Grant, GrantEntity> grantMapper;
    private final DomainToPersistenceMapper<Subject, SubjectId> subjectIdMapper;
    private final DomainToPersistenceMapper<Group, GroupId> groupIdMapper;
    private final AccessValidatorService accessValidatorService;
    private final ApplicationEventPublisher eventPublisher;

    @CacheEvict(value = "grants", key = "#accessor.subjectType.id + ':' + #accessor.id + '->' + #accessed.subjectType.id + ':' + #accessed.id + '@' + #node.value")
    @Override
    public Optional<Grant> addGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder) {
        if (existsGrant(accessor, accessed, rightsHolder)) {
            return Optional.empty();
        } else {
            try {
                // Validate if this grant can be created for this
                // combination.
                accessValidatorService.validate(accessor, accessed, rightsHolder);
            } catch (IllegalArgumentException e) {
                // Invalid grant
                return Optional.empty();
            }

            Grant grant = grantProvider.getGrant(GrantOptions.builder()
                    .accessor(accessor)
                    .accessed(accessed)
                    .rightsHolder(rightsHolder).build());

            grantRepository.save(grantMapper.toPersistence(grant));
            eventPublisher.publishEvent(new GrantAddEvent(grant));
            return Optional.of(grant);
        }
    }

    @CacheEvict(value = "grants", key = "#grant.accessor.subjectType.id + ':' + #grant.accessor.id + '->' + #grant.accessed.subjectType.id + ':' + #grant.accessed.id + '@' + #grant.node.value")
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
    public boolean existsGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder) {
        return getGrant(accessor, accessed, rightsHolder).isPresent();
    }

    @Override
    public Optional<Grant> getGrant(Subject accessor, Subject accessed, RightsHolder rightsHolder) {
        SubjectId accessorId = subjectIdMapper.toPersistence(accessor);
        SubjectId accessedId = subjectIdMapper.toPersistence(accessed);

        Optional<GrantEntity> entityOptional = Optional.empty();
        if (rightsHolder instanceof Node node) {
            entityOptional = grantRepository.findByAccessor_IdAndAccessed_IdAndNode_Value(
                    accessorId, accessedId, node.getValue());
        } else if (rightsHolder instanceof Group group) {
            GroupId groupId = groupIdMapper.toPersistence(group);

            entityOptional = grantRepository.findByAccessor_IdAndAccessed_IdAndGroup_Id(
                    accessorId, accessedId, groupId);
        }

        return entityOptional
                .map(entity -> {
                    Grant grant = grantProvider.getGrant(GrantOptions.builder()
                            .accessor(accessor)
                            .accessed(accessed)
                            .rightsHolder(rightsHolder).build());

                    return grant.isValid() ? grant : null;
                });
    }

    @Override
    public List<Grant> getGrants(Subject accessor, Subject accessed) {
        SubjectId accessorId = subjectIdMapper.toPersistence(accessor);
        SubjectId accessedId = subjectIdMapper.toPersistence(accessed);

        return grantRepository.findByAccessor_IdAndAccessed_Id(accessorId, accessedId)
                .stream()
                .map(grantMapper::toDomain)
                .filter(Grant::isValid)
                .toList();
    }

    @Override
    public int getGrantsCount(Subject accessor) {
        return grantRepository.countByAccessor_Id(subjectIdMapper.toPersistence(accessor));
    }
}
