package me.zort.acs.api.domain.mapper;

import me.zort.acs.data.id.GroupId;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.SubjectType;

public interface DomainGroupIdMapper extends DomainToPersistenceMapper<Group, GroupId> {

    GroupId toPersistence(SubjectType subjectType, String name);
}
