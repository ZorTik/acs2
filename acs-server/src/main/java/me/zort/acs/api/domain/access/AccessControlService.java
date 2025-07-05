package me.zort.acs.api.domain.access;

import me.zort.acs.api.domain.access.request.AccessRequest;

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
}
