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
        return nodeRepository.findById(domain.getValue())
                .orElseGet(() -> new NodeEntity(domain.getValue(), new ArrayList<>()));
    }

    @Override
    public Node toDomain(NodeEntity persistence) {
        return new Node(persistence.getValue());
    }
}
