package me.zort.acs.api.domain.mapper;

import me.zort.acs.api.domain.group.Group;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.data.id.DynamicGroupId;
import me.zort.acs.domain.model.Subject;

public interface DomainDynamicGroupIdMapper extends DomainToPersistenceMapper<Group, DynamicGroupId> {

    DynamicGroupId toPersistence(Subject subject, String name);
}
