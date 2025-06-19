package me.zort.acs.domain.access.rights.type;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.access.strategy.RightsStrategy;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.core.domain.mapper.PersistenceToDomainMapper;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.entity.NodeEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.grant.type.NodeGrant;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class NodeType implements RightsHolderType<Node> {
    private final GrantRepository grantRepository;
    private final RightsStrategy rightsStrategy;
    private final PersistenceToDomainMapper<NodeEntity, Node> nodeMapper;

    @Override
    public Grant createGrantFromHolder(Node holder, GrantOptions options) {
        return new NodeGrant(options.getId(), options.getAccessor(), options.getAccessed(), holder);
    }

    @Override
    public Optional<GrantEntity> getGrantEntitiesForHolder(Node holder, SubjectId accessorId, SubjectId accessedId) {
        return grantRepository.findNodeGrant(accessorId, accessedId, holder.getValue());
    }

    @Override
    public List<GrantEntity> getGrantEntitiesForHolders(List<Node> holders, SubjectId accessorId, SubjectType accessedType) {
        return grantRepository.findAllBetween(accessorId, accessedType.getId())
                .stream()
                .filter(grant -> holders
                        .stream()
                        .anyMatch(node -> rightsStrategy.isNodeApplicableOn(nodeMapper.toDomain(grant.getNode()), node)))
                .toList();
    }

    @Override
    public boolean isPresentInSubjectType(Node holder, SubjectType subjectType) {
        return subjectType.containsNode(holder);
    }

    @Override
    public Class<Node> getHolderType() {
        return Node.class;
    }
}
