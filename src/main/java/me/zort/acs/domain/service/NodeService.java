package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.data.repository.NodeRepository;
import me.zort.acs.domain.mapper.DomainNodeMapper;
import me.zort.acs.domain.mapper.DomainSubjectTypeMapper;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.NodeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class NodeService {
    private final NodeRepository nodeRepository;
    private final DomainNodeMapper nodeMapper;
    private final NodeProvider nodeProvider;
    private final DomainSubjectTypeMapper subjectTypeMapper;

    @CacheEvict(value = "nodes", key = "#value")
    public Optional<Node> createNode(String value) {
        if (existsNode(value)) {
            return Optional.empty();
        }

        Node node = nodeProvider.getNode(value);

        nodeRepository.save(nodeMapper.toPersistence(node));

        return Optional.of(node);
    }

    @CacheEvict(value = "nodes", key = "#node.value")
    public void assignNode(Node node, SubjectType subjectType) {
        NodeEntity nodeEntity = nodeMapper.toPersistence(node);
        SubjectTypeEntity subjectTypeEntity = subjectTypeMapper.toPersistence(subjectType);

        if (nodeEntity.getSubjectTypes().contains(subjectTypeEntity)) {
            return;
        }

        nodeEntity.getSubjectTypes().add(subjectTypeEntity);

        nodeRepository.save(nodeEntity);
    }

    public boolean isNodeAssigned(Node node, SubjectType subjectType) {
        NodeEntity nodeEntity = nodeMapper.toPersistence(node);
        SubjectTypeEntity subjectTypeEntity = subjectTypeMapper.toPersistence(subjectType);

        return subjectTypeEntity.getNodes().contains(nodeEntity);
    }

    public Optional<Node> getNode(String value) {
        return nodeRepository.findById(value).map(nodeMapper::toDomain);
    }

    public boolean existsNode(String value) {
        return nodeRepository.existsById(value);
    }
}
