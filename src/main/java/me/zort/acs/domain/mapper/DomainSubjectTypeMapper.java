package me.zort.acs.domain.mapper;

import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.stereotype.Component;

@Component
public class DomainSubjectTypeMapper implements DomainModelMapper<SubjectType, SubjectTypeEntity> {

    @Override
    public SubjectTypeEntity toPersistence(SubjectType domain) {
        SubjectTypeEntity entity = new SubjectTypeEntity();

        entity.setId(domain.getId());

        return entity;
    }

    @Override
    public SubjectType toDomain(SubjectTypeEntity persistence) {
        return new SubjectType(persistence.getId());
    }
}
