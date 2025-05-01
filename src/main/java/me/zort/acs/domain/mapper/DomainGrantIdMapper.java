package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Grant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGrantIdMapper implements DomainModelMapper<Grant, GrantId> {
    private final DomainSubjectIdMapper subjectIdMapper;

    @Override
    public GrantId toPersistence(Grant domain) {
        SubjectId accessorId = subjectIdMapper.toPersistence(domain.getAccessor());
        SubjectId accessedId = subjectIdMapper.toPersistence(domain.getAccessed());

        return new GrantId(accessorId, accessedId, domain.getNode().getValue());
    }
}
