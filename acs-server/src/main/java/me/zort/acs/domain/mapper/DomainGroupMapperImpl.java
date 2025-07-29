package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.service.PersistenceEntityProvider;
import me.zort.acs.api.domain.group.CreateGroupOptions;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.domain.group.GroupImpl;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGroupMapperImpl implements DomainGroupMapper {
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainModelMapper<Node, NodeEntity> nodeMapper;
    private final PersistenceEntityProvider persistenceEntityProvider;

    @Override
    public GroupEntity toPersistence(Group domain) {
        GroupEntity entity = persistenceEntityProvider.getCachedOrCreate(GroupEntity.class, domain.getId());
        entity.setId(domain.getId());
        entity.setSubjectType(subjectTypeMapper.toPersistence(domain.getSubjectType()));
        entity.setSubject(subjectMapper.toPersistence(domain.getSubject()));
        entity.setNodes(domain.getNodes()
                .stream()
                .map(nodeMapper::toPersistence).collect(Collectors.toSet()));

        if (domain.getParent() != null && domain.getParent() instanceof GroupImpl staticGroupParent) {
            entity.setParent(toPersistence(staticGroupParent));
        }

        return entity;
    }

    @Override
    public Group toDomain(GroupEntity persistence) {
        SubjectType subjectType = subjectTypeMapper.toDomain(persistence.getSubjectType());
        Subject subject = subjectMapper.toDomain(persistence.getSubject());
        Set<Node> nodes = persistence.getNodes()
                .stream()
                .map(nodeMapper::toDomain).collect(Collectors.toSet());

        Group parentGroup = null;
        if (persistence.getParent() != null) {
            // Recursively fill
            parentGroup = toDomain(persistence.getParent());
        }

        return new GroupImpl(persistence.getId(), subjectType, subject, persistence.getName(), nodes, parentGroup);
    }

    @Override
    public Group toDomain(CreateGroupOptions createOptions) {
        if (createOptions.getSubjectType() == null && createOptions.getSubject() == null) {
            throw new IllegalArgumentException("Either subjectType or subject must be provided.");
        }

        return new GroupImpl(
                UUID.randomUUID(),
                createOptions.getSubjectType(),
                createOptions.getSubject(),
                createOptions.getName(), Set.copyOf(createOptions.getNodes()), createOptions.getParentGroup());
    }
}
