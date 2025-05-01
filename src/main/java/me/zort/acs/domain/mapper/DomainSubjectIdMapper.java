package me.zort.acs.domain.mapper;

import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class DomainSubjectIdMapper implements DomainModelMapper<Subject, SubjectId> {

    @Override
    public SubjectId toPersistence(Subject domain) {
        return new SubjectId(domain.getId(), domain.getSubjectTypeId());
    }
}
