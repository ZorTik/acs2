package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.NodeRepository;
import me.zort.acs.api.domain.access.rights.RightsHolderPresenceVerifier;
import me.zort.acs.core.domain.mapper.DomainModelMapper;
import me.zort.acs.api.domain.provider.NodeProvider;
import me.zort.acs.api.domain.service.NodeService;
import me.zort.acs.data.entity.GroupEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.entity.SubjectTypeEntity;
import me.zort.acs.domain.group.Group;
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
    private final DomainModelMapper<Group, GroupEntity> groupMapper;
    private final RightsHolderPresenceVerifier rightsHolderPresenceVerifier;

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
        if (subjectType.containsNode(node)) {
            return;
        }

        NodeEntity nodeEntity = nodeMapper.toPersistence(node);
        nodeEntity.getSubjectTypes().add(subjectTypeMapper.toPersistence(subjectType));

        nodeRepository.save(nodeEntity);

        subjectType.addNode(node);
    }

    @CacheEvict(value = "nodes", key = "#node.value")
    @Override
    public void assignNode(Node node, Group group) {
        if (group.containsNode(node)) {
            return;
        }

        if (!rightsHolderPresenceVerifier.isPresentInSubjectType(group.getSubjectType(), node)) {
            throw new IllegalArgumentException(String.format(
                    "Node %s is not present in the subject type %s of the group %s.",
                    node.getValue(), group.getSubjectType().getId(), group.getName()));
        }

        NodeEntity nodeEntity = nodeMapper.toPersistence(node);
        nodeEntity.getGroups().add(groupMapper.toPersistence(group));

        nodeRepository.save(nodeEntity);

        group.addNode(node);
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
