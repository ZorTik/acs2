package me.zort.acs.domain.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.NodeProvider;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.provider.options.NodeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DomainNodeMapper implements DomainModelMapper<Node, NodeEntity> {
    private final NodeProvider nodeProvider;

    @Override
    public NodeEntity toPersistence(Node domain) {
        NodeEntity entity = new NodeEntity();

        entity.setValue(domain.getValue());

        return entity;
    }

    @Override
    public Node toDomain(NodeEntity persistence) {
        return nodeProvider.getNode(NodeOptions.builder()
                .value(persistence.getValue())
                .build());
    }
}
