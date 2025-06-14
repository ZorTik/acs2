package me.zort.acs.domain.access.rights.type;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.grant.type.GroupGrant;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GroupType implements RightsHolderType<Group> {
    private final GrantRepository grantRepository;
    private final DomainToPersistenceMapper<Group, GroupId> groupIdMapper;

    @Override
    public Grant createGrantFromHolder(Group holder, GrantOptions options) {
        return new GroupGrant(options.getId(), options.getAccessor(), options.getAccessed(), holder);
    }

    @Override
    public Optional<GrantEntity> findGrantEntityForHolder(Group holder, SubjectId accessorId, SubjectId accessedId) {
        return grantRepository
                .findByAccessor_IdAndAccessed_IdAndGroup_Id(accessorId, accessedId, groupIdMapper.toPersistence(holder));
    }

    @Override
    public boolean isPresentInSubjectType(Group holder, SubjectType subjectType) {
        return subjectType.equals(holder.getSubjectType());
    }

    @Override
    public Class<Group> getHolderType() {
        return Group.class;
    }
}
