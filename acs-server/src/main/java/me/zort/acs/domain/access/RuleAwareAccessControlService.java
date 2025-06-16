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
