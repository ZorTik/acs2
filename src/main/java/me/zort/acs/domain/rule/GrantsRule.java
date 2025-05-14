package me.zort.acs.domain.rule;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.AccessRequest;
import me.zort.acs.domain.check.RightsStrategy;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.service.GrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GrantsRule implements AccessRule {
    private final GrantService grantService;
    private final RightsStrategy rightsStrategy;

    @Override
    public void onRequest(AccessRequest request) {
        List<Grant> grants = grantService.getGrants(request.getAccessor(), request.getAccessed());

        if (grants
                .stream()
                .anyMatch(grant -> rightsStrategy.isNodeApplicableOn(grant.getNode(), request.getNode()))) {
            request.grant();
        }
    }
}
