package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.grant.RightsHolderAdapter;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.*;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RightsHolderAdapterImpl extends RightsHolderAdapter {
    private final GrantRepository grantRepository;
    private final DomainToPersistenceMapper<Group, GroupId> groupIdMapper;

    @Override
    public @Nullable Grant createGrant(GrantOptions options) {
        RightsHolder rightsHolder = options.getRightsHolder();
        if (rightsHolder instanceof Node node) {
            return new NodeGrant(options.getId(), options.getAccessor(), options.getAccessed(), node);
        } else if (rightsHolder instanceof Group group) {
            return new GroupGrant(options.getId(), options.getAccessor(), options.getAccessed(), group);
        }

        return null;
    }

    @Override
    public Optional<GrantEntity> getGrantEntity(SubjectId accessorId, SubjectId accessedId, RightsHolder rightsHolder) {
        Optional<GrantEntity> entityOptional = Optional.empty();
        if (rightsHolder instanceof Node node) {
            entityOptional = grantRepository
                    .findByAccessor_IdAndAccessed_IdAndNode_Value(accessorId, accessedId, node.getValue());
        } else if (rightsHolder instanceof Group group) {
            entityOptional = grantRepository
                    .findByAccessor_IdAndAccessed_IdAndGroup_Id(accessorId, accessedId, groupIdMapper.toPersistence(group));
        }

        return entityOptional;
    }

    @Override
    public boolean isPresentInSubjectType(SubjectType subjectType, RightsHolder rightsHolder) {
        if (rightsHolder instanceof Node node) {
            return subjectType.containsNode(node);
        }
        if (rightsHolder instanceof Group group) {
            return subjectType.equals(group.getSubjectType());
        }

        return false;
    }

    @Override
    protected Class<?>[] getSupportedHolderTypes() {
        return new Class[] { Group.class, Node.class };
    }
}
