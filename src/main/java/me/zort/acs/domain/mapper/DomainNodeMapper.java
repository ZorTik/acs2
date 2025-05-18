package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainNodeMapper implements DomainModelMapper<Node, NodeEntity> {
    @Override
    public NodeEntity toPersistence(Node domain) {
        NodeEntity entity = new NodeEntity();

        entity.setValue(domain.getValue());

        return entity;
    }

    @Override
    public Node toDomain(NodeEntity persistence) {
        return new Node(persistence.getValue(), persistence.getSubjectTypes()
                .stream()
                .map(SubjectTypeEntity::getId)
                .collect(Collectors.toCollection(ArrayList::new)));
    }
}
