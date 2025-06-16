package me.zort.acs.domain.access;

import me.zort.acs.api.domain.access.AccessControlService;
import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.api.domain.access.AccessRequestFactory;
import me.zort.acs.domain.model.Node;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.access.rule.AccessRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service implementation of {@link AccessControlService} that evaluates access requests
 * using a list of {@link AccessRule} instances.
 * <p>
 * This service processes access requests by applying each configured access rule in order.
 * The evaluation stops as soon as any rule grants access.
 * </p>
 * <p>
 * It also provides a method to retrieve the grant states for all nodes associated with
 * a given subject type, based on the provided accessor and accessed subjects.
 * </p>
 */
@Service
public class RuleAwareAccessControlService implements AccessControlService {
    private final AccessRequestFactory accessRequestFactory;
    private final List<AccessRule> accessRules;

    @Autowired
    public RuleAwareAccessControlService(AccessRequestFactory accessRequestFactory, List<AccessRule> accessRules) {
        this.accessRequestFactory = accessRequestFactory;
        this.accessRules = accessRules;
    }

    @Override
    public void checkAccess(AccessRequest request) {
        for (AccessRule rule : accessRules) {
            rule.onRequest(request);

            if (request.isGranted()) {
                break;
            }
        }
    }

    @Override
    public Map<Node, Boolean> getGrantStatesFor(SubjectLike accessor, SubjectLike accessed) {
        return accessed.getSubjectType().getNodes()
                .stream()
                .collect(Collectors.toMap(Function.identity(), node -> {
                    AccessRequest request = accessRequestFactory.createAccessRequest(accessor, accessed, node);
                    checkAccess(request);

                    return request.isGranted();
                }));
    }
}
