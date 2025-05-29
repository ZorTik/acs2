package me.zort.acs.domain.access;

import me.zort.acs.api.domain.access.AccessRequest;
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
public class AccessControlService {
    private final AccessRequestFactory accessRequestFactory;
    private final List<AccessRule> accessRules;

    @Autowired
    public AccessControlService(AccessRequestFactory accessRequestFactory, List<AccessRule> accessRules) {
        this.accessRequestFactory = accessRequestFactory;
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
        AccessRequest request = accessRequestFactory.createAccessRequest(accessor, accessed, node);

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

    /**
     * Returns ALL nodes that are assignable to accessed's subject type and their
     * grant states in relation with accessor.
     *
     * @param accessor The accessor subject
     * @param accessed The accessed subject
     * @return The nodes and their states
     */
    public Map<Node, Boolean> getGrantStatesFor(SubjectLike accessor, SubjectLike accessed) {
        return accessed.getSubjectType().getNodes()
                .stream()
                .collect(Collectors.toMap(Function.identity(), node -> {
                    // There is no connection between them since either of them is not present
                    // in the system.
                    if (accessed.isNull() || accessor.isNull()) {
                        return false;
                    }

                    return checkAccess(accessor, accessed, node).isGranted();
                }));
    }
}
