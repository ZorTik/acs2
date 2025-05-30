package me.zort.acs.domain.access.rule;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsNegotiationService;
import me.zort.acs.api.domain.access.request.SubjectToSubjectAccessRequest;
import me.zort.acs.api.domain.access.strategy.RightsStrategy;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class RightsRule extends SubjectToSubjectAccessRule {
    private final RightsNegotiationService rightsNegotiationService;
    private final RightsStrategy rightsStrategy;

    @Override
    public void onRequest(SubjectToSubjectAccessRequest request) {
        if (request.getAccessor() instanceof Subject accessor
                && request.getAccessed() instanceof Subject accessed
                && hasRights(accessor, accessed, request.getNode())) {
            request.grant();
        }
    }

    private boolean hasRights(Subject accessor, Subject accessed, Node requestedNode) {
        // Check if the accessor has rights on the accessed subject by
        // applying the owned nodes to the requested node.
        return rightsNegotiationService.getRightsHolders(accessor, accessed)
                .stream()
                .flatMap(holder -> holder.getGrantedNodes().stream())
                .anyMatch(node -> rightsStrategy.isNodeApplicableOn(node, requestedNode));
    }
}
