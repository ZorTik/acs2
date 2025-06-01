package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.NodeRepository;
import me.zort.acs.api.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.NodeProvider;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.NodeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final DomainModelMapper<Node, NodeEntity> nodeMapper;
    private final NodeProvider nodeProvider;
    private final DomainModelMapper<SubjectType, SubjectTypeEntity> subjectTypeMapper;

    @CacheEvict(value = "nodes", key = "#value")
    @Override
    public Node createNode(String value) {
        return getNode(value).orElseGet(() -> {
            Node node = nodeProvider.getNode(NodeOptions.builder()
                    .value(value)
                    .build());

            nodeRepository.save(nodeMapper.toPersistence(node));

            return node;
        });
    }

    @CacheEvict(value = "nodes", key = "#node.value")
    @Override
    public void assignNode(Node node, SubjectType subjectType) {
        NodeEntity nodeEntity = nodeMapper.toPersistence(node);
        SubjectTypeEntity subjectTypeEntity = subjectTypeMapper.toPersistence(subjectType);

        if (nodeEntity.getSubjectTypes().contains(subjectTypeEntity)) {
            return;
        }

        nodeEntity.getSubjectTypes().add(subjectTypeEntity);

        nodeRepository.save(nodeEntity);
    }

    @Override
    public Optional<Node> getNode(String value) {
        return nodeRepository.findById(value).map(nodeMapper::toDomain);
    }

    @Override
    public boolean existsNode(String value) {
        return nodeRepository.existsById(value);
    }
}
