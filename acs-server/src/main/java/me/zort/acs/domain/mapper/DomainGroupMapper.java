package me.zort.acs.domain.mapper;

import me.zort.acs.api.domain.group.CreateGroupOptions;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.data.entity.GroupEntity;

public interface DomainGroupMapper extends DomainModelMapper<Group, GroupEntity> {

    Group toDomain(CreateGroupOptions createOptions);
}
