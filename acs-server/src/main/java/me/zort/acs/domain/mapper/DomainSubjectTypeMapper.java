package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.SubjectTypeProvider;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.SubjectTypeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainSubjectTypeMapper implements DomainModelMapper<SubjectType, SubjectTypeEntity> {
    private final DomainModelMapper<Node, NodeEntity> nodeMapper;
    private final SubjectTypeProvider subjectTypeProvider;

    @Override
    public SubjectTypeEntity toPersistence(SubjectType domain) {
        SubjectTypeEntity entity = new SubjectTypeEntity();
        entity.setId(domain.getId());
        entity.setNodes(domain.getNodes()
                .stream()
                .map(nodeMapper::toPersistence)
                .collect(Collectors.toSet()));
        return entity;
    }

    @Override
    public SubjectType toDomain(SubjectTypeEntity persistence) {
        List<Node> nodes = persistence.getNodes()
                .stream()
                .map(nodeMapper::toDomain).toList();

        return subjectTypeProvider.getSubjectType(SubjectTypeOptions.builder()
                .id(persistence.getId())
                .nodes(nodes).build());
    }
}
