package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.provider.SubjectProvider;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainSubjectMapper implements DomainModelMapper<Subject, SubjectEntity> {
    private final DomainToPersistenceMapper<Subject, SubjectId> subjectIdMapper;
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;
    private final SubjectProvider subjectProvider;

    @Override
    public SubjectEntity toPersistence(Subject domain) {
        SubjectEntity entity = new SubjectEntity();

        SubjectId id = subjectIdMapper.toPersistence(domain);
        entity.setId(id);

        SubjectTypeEntity subjectType = subjectTypeMapper.toPersistence(domain.getSubjectType());
        entity.setSubjectType(subjectType);

        return entity;
    }

    @Override
    public Subject toDomain(SubjectEntity persistence) {
        SubjectType subjectType = subjectTypeMapper.toDomain(persistence.getSubjectType());

        return subjectProvider.getSubject(subjectType, persistence.getSubjectId());
    }
}
