package me.zort.acs.domain.grant;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.grant.GrantAdapter;
import me.zort.acs.api.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.GroupGrant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.NodeGrant;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GrantAdapterImpl extends GrantAdapter {
    private final GrantRepository grantRepository;
    private final DomainToPersistenceMapper<Group, GroupId> groupIdMapper;

    @Override
    protected Grant doCreateGrant(GrantOptions options) {
        RightsHolder rightsHolder = options.getRightsHolder();

        if (rightsHolder instanceof Node node) {
            return new NodeGrant(options.getId(), options.getAccessor(), options.getAccessed(), node);
        } else {
            Group group = (Group) rightsHolder;

            return new GroupGrant(options.getId(), options.getAccessor(), options.getAccessed(), group);
        }
    }

    @Override
    protected Optional<GrantEntity> doGetGrantEntity(SubjectId accessorId, SubjectId accessedId, RightsHolder rightsHolder) {
        Optional<GrantEntity> entityOptional = Optional.empty();
        if (rightsHolder instanceof Node node) {
            entityOptional = grantRepository
                    .findByAccessor_IdAndAccessed_IdAndNode_Value(accessorId, accessedId, node.getValue());
        } else if (rightsHolder instanceof Group group) {
            GroupId groupId = groupIdMapper.toPersistence(group);

            entityOptional = grantRepository
                    .findByAccessor_IdAndAccessed_IdAndGroup_Id(accessorId, accessedId, groupId);
        }

        return entityOptional;
    }

    @Override
    protected Class<?>[] getSupportedGrantTypes() {
        return new Class[] { Group.class, Node.class };
    }
}
