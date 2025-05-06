package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainNodeMapper implements DomainModelMapper<Node, NodeEntity> {
    private final DomainSubjectTypeMapper subjectTypeMapper;

    @Override
    public NodeEntity toPersistence(Node domain) {
        NodeEntity entity = new NodeEntity();

        entity.setValue(domain.getValue());
        entity.setSubjectTypes(domain.getSubjectTypes()
                .stream()
                .map(subjectTypeMapper::toPersistence)
                .toList());

        return entity;
    }

    @Override
    public Node toDomain(NodeEntity persistence) {
        List<SubjectType> subjectTypes = persistence.getSubjectTypes()
                .stream()
                .map(subjectTypeMapper::toDomain)
                .toList();

        return new Node(persistence.getValue(), subjectTypes);
    }
}
