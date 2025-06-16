package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.rights.RightsNegotiationService;
import me.zort.acs.api.domain.access.request.SubjectToSubjectAccessRequest;
import me.zort.acs.api.domain.access.strategy.RightsStrategy;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RightsRule extends SubjectToSubjectAccessRule {
    private final RightsNegotiationService rightsNegotiationService;
    private final RightsStrategy rightsStrategy;

    @Autowired
    public RightsRule(RightsNegotiationService rightsNegotiationService, RightsStrategy rightsStrategy) {
        super(true);
        this.rightsNegotiationService = rightsNegotiationService;
        this.rightsStrategy = rightsStrategy;
    }

    @Override
    public void onRequest(SubjectToSubjectAccessRequest request) {
        if (hasRights(request.getAccessor(), request.getAccessed(), request.getNode())) {
            request.grant();
        }
    }

    private boolean hasRights(SubjectLike accessor, SubjectLike accessed, Node requestedNode) {
        // Check if the accessor has rights on the accessed subject by
        // applying the owned nodes to the requested node.
        return rightsNegotiationService.getRightsHolders(accessor, accessed)
                .stream()
                .flatMap(holder -> holder.getGrantedNodes().stream())
                .anyMatch(node -> rightsStrategy.isNodeApplicableOn(node, requestedNode));
    }
}
