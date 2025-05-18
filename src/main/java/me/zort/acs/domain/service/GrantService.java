package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.data.repository.GrantRepository;
import me.zort.acs.domain.event.GrantAddEvent;
import me.zort.acs.domain.event.GrantRemoveEvent;
import me.zort.acs.domain.mapper.DomainGrantIdMapper;
import me.zort.acs.domain.mapper.DomainGrantMapper;
import me.zort.acs.domain.mapper.DomainSubjectIdMapper;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.GrantProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GrantService {
    private final GrantRepository grantRepository;
    private final GrantProvider grantProvider;
    private final DomainGrantIdMapper grantIdMapper;
    private final DomainGrantMapper grantMapper;
    private final DomainSubjectIdMapper subjectIdMapper;
    private final ApplicationEventPublisher eventPublisher;

    @CacheEvict(value = "grants", key = "#accessor.subjectType.id + ':' + #accessor.id + '->' + #accessed.subjectType.id + ':' + #accessed.id + '@' + #node.value")
    public Optional<Grant> addGrant(Subject accessor, Subject accessed, Node node) {
        Grant grant = grantProvider.getGrant(accessor, accessed, node);

        if (existsGrant(grant)) {
            return Optional.empty();
        } else {
            grantRepository.save(grantMapper.toPersistence(grant));

            eventPublisher.publishEvent(new GrantAddEvent(grant));
            return Optional.of(grant);
        }
    }

    @CacheEvict(value = "grants", key = "#grant.accessor.subjectType.id + ':' + #grant.accessor.id + '->' + #grant.accessed.subjectType.id + ':' + #grant.accessed.id + '@' + #grant.node.value")
    public boolean removeGrant(Grant grant) {
        GrantId id = grantIdMapper.toPersistence(grant);

        if (grantRepository.existsById(id)) {
            grantRepository.deleteById(id);

            // Publish remove event to clear potential resources without any links.
            eventPublisher.publishEvent(new GrantRemoveEvent(grant));
            return true;
        } else {
            return false;
        }
    }

    public boolean existsGrant(Grant grant) {
        return grantRepository.existsById(grantIdMapper.toPersistence(grant));
    }

    public Optional<Grant> getGrant(Subject accessor, Subject accessed, Node node) {
        Grant grant = grantProvider.getGrant(accessor, accessed, node);

        if (existsGrant(grant)) {
            return Optional.of(grant);
        } else {
            return Optional.empty();
        }
    }

    public List<Grant> getGrants(Subject accessor, Subject accessed) {
        SubjectId accessorId = subjectIdMapper.toPersistence(accessor);
        SubjectId accessedId = subjectIdMapper.toPersistence(accessed);

        return grantRepository.findByAccessor_IdAndAccessed_Id(accessorId, accessedId)
                .stream()
                .map(grantMapper::toDomain)
                .toList();
    }

    public int getGrantsCount(Subject accessor) {
        return grantRepository.countByAccessor_Id(subjectIdMapper.toPersistence(accessor));
    }
}
