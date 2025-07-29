package me.zort.acs.domain.access.rights.type;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.core.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GroupType implements RightsHolderType<Group> {
    private final GrantRepository grantRepository;
    private final PersistenceToDomainMapper<GrantEntity, Grant> grantMapper;

    @Override
    public Optional<Grant> getGrantForHolder(Group holder, SubjectId accessorId, SubjectId accessedId) {
        return grantRepository.findGroupGrant(accessorId, accessedId, holder.getId()).map(grantMapper::toDomain);
    }

    @Override
    public List<Grant> getGrantsForHolders(List<Group> holders, SubjectId accessorId, SubjectType accessedType) {
        List<UUID> groupIds = holders.stream().map(Group::getId).toList();

        return grantRepository.findAllByGroupIn(accessorId, accessedType.getId(), groupIds)
                .stream()
                .map(grantMapper::toDomain).toList();
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
