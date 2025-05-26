package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.api.domain.provider.GroupProvider;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GroupOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainGroupMapper implements PersistenceToDomainMapper<GroupEntity, Group> {
    private final GroupProvider groupProvider;
    private final PersistenceToDomainMapper<SubjectTypeEntity, SubjectType> subjectTypeMapper;
    private final PersistenceToDomainMapper<NodeEntity, Node> nodeMapper;

    @Override
    public Group toDomain(GroupEntity persistence) {
        SubjectType subjectType = subjectTypeMapper.toDomain(persistence.getSubjectType());
        Set<Node> nodes = persistence.getNodes()
                .stream()
                .map(nodeMapper::toDomain).collect(Collectors.toSet());

        Group parentGroup = null;
        if (persistence.getParent() != null) {
            parentGroup = toDomain(persistence.getParent());
        }

        return groupProvider.getGroup(GroupOptions.builder()
                .subjectType(subjectType)
                .name(persistence.getName())
                .parentGroup(parentGroup)
                .nodes(nodes).build());
    }
}
