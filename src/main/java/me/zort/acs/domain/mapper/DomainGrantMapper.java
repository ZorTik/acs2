package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.id.GrantId;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.provider.GrantProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGrantMapper implements DomainModelMapper<Grant, GrantEntity> {
    private final DomainGrantIdMapper grantIdMapper;
    private final DomainSubjectMapper subjectMapper;
    private final DomainNodeMapper nodeMapper;

    private final GrantProvider grantProvider;

    @Override
    public GrantEntity toPersistence(Grant domain) {
        GrantEntity entity = new GrantEntity();

        GrantId id = grantIdMapper.toPersistence(domain);
        entity.setId(id);

        SubjectEntity accessor = subjectMapper.toPersistence(domain.getAccessor());
        entity.setAccessor(accessor);

        SubjectEntity accessed = subjectMapper.toPersistence(domain.getAccessed());
        entity.setAccessed(accessed);

        NodeEntity node = nodeMapper.toPersistence(domain.getNode());
        entity.setNode(node);

        return entity;
    }

    @Override
    public Grant toDomain(GrantEntity persistence) {
        Subject accessor = subjectMapper.toDomain(persistence.getAccessor());
        Subject accessed = subjectMapper.toDomain(persistence.getAccessed());
        Node node = nodeMapper.toDomain(persistence.getNode());

        return grantProvider.getGrant(accessor, accessed, node);
    }
}
