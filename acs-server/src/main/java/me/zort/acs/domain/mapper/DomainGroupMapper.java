package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.service.PersistenceEntityProvider;
import me.zort.acs.api.domain.mapper.DomainGroupIdMapper;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.data.id.GroupId;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGroupMapper implements DomainModelMapper<Group, GroupEntity> {
    private final GroupProvider groupProvider;
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;
    private final DomainModelMapper<Node, NodeEntity> nodeMapper;
    private final DomainGroupIdMapper groupIdMapper;
    private final PersistenceEntityProvider persistenceEntityProvider;

    @Override
    public GroupEntity toPersistence(Group domain) {
        GroupId id = groupIdMapper.toPersistence(domain.getSubjectType(), domain.getName());
        GroupEntity entity = persistenceEntityProvider.getCachedOrCreate(GroupEntity.class, id);
        entity.setId(id);
        entity.setName(domain.getName());
        entity.setSubjectType(subjectTypeMapper.toPersistence(domain.getSubjectType()));
        entity.setNodes(domain.getNodes()
                .stream()
                .map(nodeMapper::toPersistence).collect(Collectors.toSet()));

        if (domain.getParent() != null) {
            entity.setParent(toPersistence(domain.getParent()));
        }

        return entity;
    }

    @Override
    public Group toDomain(GroupEntity persistence) {
        SubjectType subjectType = subjectTypeMapper.toDomain(persistence.getSubjectType());
        Set<Node> nodes = persistence.getNodes()
                .stream()
                .map(nodeMapper::toDomain).collect(Collectors.toSet());

        Group parentGroup = null;
        if (persistence.getParent() != null) {
            // Recursively fill
            parentGroup = toDomain(persistence.getParent());
        }

        return groupProvider.getGroup(GroupOptions.builder()
                .subjectType(subjectType)
                .name(persistence.getName())
                .parentGroup(parentGroup)
                .nodes(nodes).build());
    }
}
