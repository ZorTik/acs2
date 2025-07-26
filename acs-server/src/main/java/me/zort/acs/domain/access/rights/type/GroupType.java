package me.zort.acs.domain.access.rights.type;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.core.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.DynamicGroupId;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.group.DynamicGroup;
import me.zort.acs.domain.group.StaticGroup;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GroupType implements RightsHolderType<Group> {
    private final GrantRepository grantRepository;
    private final DomainToPersistenceMapper<Group, GroupId> groupIdMapper;
    private final DomainToPersistenceMapper<Group, DynamicGroupId> dynamicGroupIdMapper;
    private final PersistenceToDomainMapper<GrantEntity, Grant> grantMapper;

    @Override
    public Optional<Grant> getGrantForHolder(Group holder, SubjectId accessorId, SubjectId accessedId) {
        Optional<GrantEntity> grantEntity = Optional.empty();
        if (holder instanceof StaticGroup) {
            grantEntity = grantRepository.findGroupGrant(accessorId, accessedId, groupIdMapper.toPersistence(holder));
        }

        if (holder instanceof DynamicGroup) {
            grantEntity = grantRepository.findDynamicGroupGrant(accessorId, accessedId, dynamicGroupIdMapper.toPersistence(holder));
        }

        return grantEntity.map(grantMapper::toDomain);
    }

    @Override
    public List<Grant> getGrantsForHolders(List<Group> holders, SubjectId accessorId, SubjectType accessedType) {
        List<GrantEntity> grantEntities = new ArrayList<>();

        List<GroupId> groupIds = holders
                .stream()
                .filter(group -> group instanceof StaticGroup)
                .map(groupIdMapper::toPersistence).toList();
        if (!groupIds.isEmpty()) {
            grantEntities.addAll(grantRepository.findAllByGroupIn(accessorId, accessedType.getId(), groupIds));
        }

        List<DynamicGroupId> dynamicGroupIds = holders
                .stream()
                .filter(group -> group instanceof DynamicGroup)
                .map(dynamicGroupIdMapper::toPersistence).toList();
        if (!dynamicGroupIds.isEmpty()) {
            grantEntities.addAll(grantRepository.findAllByDynamicGroupIn(accessorId, accessedType.getId(), dynamicGroupIds));
        }

        return grantEntities.stream().map(grantMapper::toDomain).toList();
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
