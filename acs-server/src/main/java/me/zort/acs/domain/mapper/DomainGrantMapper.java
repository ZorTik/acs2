package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.provider.GrantProvider;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGrantMapper implements DomainModelMapper<Grant, GrantEntity> {
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainModelMapper<Node, NodeEntity> nodeMapper;
    private final DomainModelMapper<Group, GroupEntity> groupMapper;

    private final GrantProvider grantProvider;

    @Override
    public GrantEntity toPersistence(Grant domain) {
        GrantEntity entity = new GrantEntity();

        UUID id = domain.getId();
        entity.setId(id);

        SubjectEntity accessor = subjectMapper.toPersistence(domain.getAccessor());
        entity.setAccessor(accessor);

        SubjectEntity accessed = subjectMapper.toPersistence(domain.getAccessed());
        entity.setAccessed(accessed);

        RightsHolder rightsHolder = domain.getRightsHolder();
        if (rightsHolder instanceof Node node) {
            entity.setNode(nodeMapper.toPersistence(node));
        } else if (rightsHolder instanceof Group group) {
            entity.setGroup(groupMapper.toPersistence(group));
        }

        return entity;
    }

    @Override
    public Grant toDomain(GrantEntity persistence) {
        Subject accessor = subjectMapper.toDomain(persistence.getAccessor());
        Subject accessed = subjectMapper.toDomain(persistence.getAccessed());

        RightsHolder rightsHolder = null;
        if (persistence.getGroup() != null) {
            rightsHolder = groupMapper.toDomain(persistence.getGroup());
        } else if (persistence.getNode() != null) {
            rightsHolder = nodeMapper.toDomain(persistence.getNode());
        }

        return grantProvider.getGrant(GrantOptions.builder()
                .id(persistence.getId())
                .accessor(accessor)
                .accessed(accessed)
                .rightsHolder(rightsHolder).build());
    }
}
