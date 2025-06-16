package me.zort.acs.api.domain.access;

import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Node;

import java.util.Map;

/**
 * Service interface for evaluating access control requests and retrieving grant states.
 * <p>
 * Implementations of this interface are responsible for checking access permissions
 * based on the provided {@link AccessRequest} and for returning the grant states
 * of all nodes assignable to the accessed subject type in relation to the accessor.
 * </p>
 */
public interface AccessControlService {

    /**
     * Performs a check on the given AccessRequest object.
     *
     * @param request The AccessRequest object to check
     */
    void checkAccess(AccessRequest request);

    /**
     * Returns ALL nodes that are assignable to accessed's subject type and their
     * grant states in relation with accessor.
     *
     * @param accessor The accessor subject
     * @param accessed The accessed subject
     * @return The nodes and their states
     */
    Map<Node, Boolean> getGrantStatesFor(SubjectLike accessor, SubjectLike accessed);
}
