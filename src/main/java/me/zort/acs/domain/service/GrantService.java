package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.data.repository.GrantRepository;
import me.zort.acs.domain.event.GrantRemoveEvent;
import me.zort.acs.domain.mapper.DomainGrantIdMapper;
import me.zort.acs.domain.mapper.DomainGrantMapper;
import me.zort.acs.domain.mapper.DomainSubjectIdMapper;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GrantService {
    private final GrantRepository grantRepository;
    private final DomainGrantIdMapper grantIdMapper;
    private final DomainGrantMapper grantMapper;
    private final DomainSubjectIdMapper subjectIdMapper;
    private final ApplicationEventPublisher eventPublisher;

    public boolean addGrant(Grant grant) {
        if (grantRepository.existsById(grantIdMapper.toPersistence(grant))) {
            return false;
        } else {
            grantRepository.save(grantMapper.toPersistence(grant));

            return true;
        }
    }

    public boolean removeGrant(Grant grant) {
        GrantId id = grantIdMapper.toPersistence(grant);

        if (grantRepository.existsById(id)) {
            grantRepository.deleteById(id);

            eventPublisher.publishEvent(new GrantRemoveEvent(grant));
            return true;
        } else {
            return false;
        }
    }

    public boolean existsGrant(Grant grant) {
        return grantRepository.existsById(grantIdMapper.toPersistence(grant));
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
