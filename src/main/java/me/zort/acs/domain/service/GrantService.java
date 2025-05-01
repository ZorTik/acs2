package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.data.repository.GrantRepository;
import me.zort.acs.domain.mapper.DomainGrantIdMapper;
import me.zort.acs.domain.mapper.DomainGrantMapper;
import me.zort.acs.domain.mapper.DomainSubjectIdMapper;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class GrantService {
    private final GrantRepository grantRepository;
    private final DomainGrantIdMapper grantIdMapper;
    private final DomainGrantMapper grantMapper;
    private final DomainSubjectIdMapper subjectIdMapper;

    public boolean addGrant(Grant grant) {
        GrantId id = grantIdMapper.toPersistence(grant);

        if (grantRepository.existsById(id)) {
            return false;
        }

        GrantEntity entity = grantMapper.toPersistence(grant);

        grantRepository.save(entity);

        return true;
    }

    public boolean removeGrant(Grant grant) {
        GrantId id = grantIdMapper.toPersistence(grant);

        if (!grantRepository.existsById(id)) {
            return false;
        }

        grantRepository.deleteById(id);

        return true;
    }

    public boolean existsGrant(Grant grant) {
        GrantId id = grantIdMapper.toPersistence(grant);

        return grantRepository.existsById(id);
    }

    public List<Grant> getGrants(Subject accessor, Subject accessed) {
        SubjectId accessorId = subjectIdMapper.toPersistence(accessor);
        SubjectId accessedId = subjectIdMapper.toPersistence(accessed);

        return grantRepository.findByAccessor_IdAndAccessed_Id(accessorId, accessedId)
                .stream()
                .map(grantMapper::toDomain)
                .toList();
    }
}
