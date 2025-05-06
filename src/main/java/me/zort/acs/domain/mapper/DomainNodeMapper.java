package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainNodeMapper implements DomainModelMapper<Node, NodeEntity> {
    private final DomainSubjectTypeMapper subjectTypeMapper;

    @Override
    public NodeEntity toPersistence(Node domain) {
        NodeEntity entity = new NodeEntity();

        entity.setValue(domain.getValue());
        // TODO: Fetch node types for node

        return entity;
    }

    @Override
    public Node toDomain(NodeEntity persistence) {
        return new Node(persistence.getValue());
    }
}
