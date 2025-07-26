package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.group.Group;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.core.domain.mapper.DomainToPersistenceMapper;
import me.zort.acs.data.entity.DynamicGroupEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.data.id.DynamicGroupId;
import me.zort.acs.domain.group.DynamicGroup;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DomainDynamicGroupMapper implements DomainModelMapper<Group, DynamicGroupEntity> {
    private final GroupProvider groupProvider;
    private final DomainToPersistenceMapper<Group, DynamicGroupId> idMapper;
    private final DomainToPersistenceMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;
    private final DomainModelMapper<Subject, SubjectEntity> subjectMapper;
    private final DomainModelMapper<Node, NodeEntity> nodeMapper;

    @Override
    public DynamicGroupEntity toPersistence(Group domain) {
        DynamicGroupEntity entity = new DynamicGroupEntity();
        entity.setId(idMapper.toPersistence(domain));
        entity.setSubjectType(subjectTypeMapper.toPersistence(domain.getSubjectType()));
        entity.setSubject(subjectMapper.toPersistence(domain.getSubject()));
        entity.setNodes(domain.getNodes()
                .stream()
                .map(nodeMapper::toPersistence).collect(Collectors.toSet()));

        if (domain.getParent() != null && domain.getParent() instanceof DynamicGroup staticGroupParent) {
            entity.setParent(toPersistence(staticGroupParent));
        }

        return entity;
    }

    @Override
    public Group toDomain(DynamicGroupEntity persistence) {
        Subject subject = subjectMapper.toDomain(persistence.getSubject());
        Set<Node> nodes = persistence.getNodes()
                .stream()
                .map(nodeMapper::toDomain).collect(Collectors.toSet());

        Group parentGroup = null;
        if (persistence.getParent() != null) {
            // Recursively fill
            parentGroup = toDomain(persistence.getParent());
        }

        return groupProvider.getGroup(GroupOptions.builder()
                .subject(subject)
                .name(persistence.getName())
                .parentGroup(parentGroup)
                .nodes(nodes).build());
    }
}
