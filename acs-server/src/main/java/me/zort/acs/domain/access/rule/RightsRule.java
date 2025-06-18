package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.access.rights.RightsNegotiationService;
import me.zort.acs.api.domain.access.request.SubjectToSubjectAccessRequest;
import me.zort.acs.api.domain.access.strategy.RightsStrategy;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

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
        if (hasRights(request.getAccessor(), request.getAccessed(), request.getRightsHolder())) {
            request.grant();
        }
    }

    private boolean hasRights(SubjectLike accessor, SubjectLike accessed, RightsHolder rightsHolder) {
        // Check if the accessor has rights on the accessed subject by
        // applying the owned holders to the requested holder.
        return rightsNegotiationService.getRightsHolders(accessor, accessed)
                .stream()
                .anyMatch(holder -> {
                    if (rightsHolder instanceof Node requestedNode) {
                        return holder.getGrantedNodes()
                                .stream()
                                .anyMatch(node -> rightsStrategy.isNodeApplicableOn(node, requestedNode));
                    } else {
                        return holder.equals(rightsHolder);
                    }
                });
    }

    @Override
    public Page<? extends SubjectLike> queryForAccessibleSubjects(
            SubjectLike accessor, SubjectType targetSubjectType, List<RightsHolder> rightsHolders, Pageable pageable) {
        return rightsNegotiationService.getCandidateSubjects(accessor, targetSubjectType, rightsHolders, pageable);
    }
}
