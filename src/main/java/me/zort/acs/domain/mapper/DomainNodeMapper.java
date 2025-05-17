package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.repository.NodeRepository;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainNodeMapper implements DomainModelMapper<Node, NodeEntity> {
    private final NodeRepository nodeRepository;

    @Override
    public NodeEntity toPersistence(Node domain) {
        NodeEntity entity = new NodeEntity();
        entity.setValue(domain.getValue());
        entity.setSubjectTypes(nodeRepository.findById(domain.getValue())
                .map(NodeEntity::getSubjectTypes)
                .orElseGet(ArrayList::new));

        return entity;
    }

    @Override
    public Node toDomain(NodeEntity persistence) {
        return new Node(persistence.getValue());
    }
}
