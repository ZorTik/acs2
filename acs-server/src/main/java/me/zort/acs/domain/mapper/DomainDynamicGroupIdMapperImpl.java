package me.zort.acs.domain.mapper;

import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.mapper.DomainDynamicGroupIdMapper;
import me.zort.acs.data.id.DynamicGroupId;
import me.zort.acs.domain.model.Subject;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DomainDynamicGroupIdMapperImpl implements DomainDynamicGroupIdMapper {

    @Override
    public DynamicGroupId toPersistence(Group domain) {
        return toPersistence(domain.getSubject(), domain.getName());
    }

    @Override
    public DynamicGroupId toPersistence(Subject subject, String name) {
        Objects.requireNonNull(subject, "Group subject cannot be null");

        return new DynamicGroupId(subject.getSubjectTypeId(), subject.getId(), name);
    }
}
