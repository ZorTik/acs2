package me.zort.acs.domain.mapper;

import me.zort.acs.api.domain.mapper.DomainGroupIdMapper;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.stereotype.Component;

@Component
public class DomainGroupIdMapperImpl implements DomainGroupIdMapper {

    @Override
    public GroupId toPersistence(Group domain) {
        return toPersistence(domain.getSubjectType(), domain.getName());
    }

    @Override
    public GroupId toPersistence(SubjectType subjectType, String name) {
        return new GroupId(subjectType.getId(), name);
    }
}
