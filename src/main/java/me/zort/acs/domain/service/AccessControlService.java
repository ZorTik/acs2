package me.zort.acs.domain.service;

import me.zort.acs.domain.model.AccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectLike;
import me.zort.acs.domain.provider.AccessRequestProvider;
import me.zort.acs.domain.rule.AccessRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessControlService {
    private final AccessRequestProvider accessRequestProvider;

    private final List<AccessRule> accessRules;

    public AccessControlService(AccessRequestProvider accessRequestProvider, List<AccessRule> accessRules) {
        this.accessRequestProvider = accessRequestProvider;
        this.accessRules = accessRules;
    }

    /**
     * Creates an AccessRequest object with the given parameters and checks if is granted.
     *
     * @param accessor The accessing object
     * @param accessed The accessed object
     * @param node The node to check applicability for
     * @return An AccessRequest object with the given parameters and is granted if true.
     * @throws IllegalArgumentException if the node is not applicable on the accessed object
     */
    public AccessRequest checkAccess(SubjectLike accessor, SubjectLike accessed, Node node) {
        AccessRequest request = accessRequestProvider.createAccessRequest(accessor, accessed, node);

        checkAccess(request);

        return request;
    }

    /**
     * Performs a check on the given AccessRequest object.
     *
     * @param request The AccessRequest object to check
     */
    public void checkAccess(AccessRequest request) {
        boolean hasNullableSubjects = request.getAccessor().isNull() || request.getAccessed().isNull();

        for (AccessRule rule : accessRules) {
            if (hasNullableSubjects && !rule.acceptsNullableSubjects()) {
                continue;
            }

            rule.onRequest(request);

            if (request.isGranted()) {
                break;
            }
        }
    }
}
