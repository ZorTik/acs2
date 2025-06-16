package me.zort.acs.domain.access.rights.type;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.data.repository.GrantRepository;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.data.entity.GrantEntity;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.grant.type.NodeGrant;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class NodeType implements RightsHolderType<Node> {
    private final GrantRepository grantRepository;

    @Override
    public Grant createGrantFromHolder(Node holder, GrantOptions options) {
        return new NodeGrant(options.getId(), options.getAccessor(), options.getAccessed(), holder);
    }

    @Override
    public Optional<GrantEntity> findGrantEntityForHolder(Node holder, SubjectId accessorId, SubjectId accessedId) {
        return grantRepository
                .findByAccessor_IdAndAccessed_IdAndNode_Value(accessorId, accessedId, holder.getValue());
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
