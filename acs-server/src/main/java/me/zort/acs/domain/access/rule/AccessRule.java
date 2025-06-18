package me.zort.acs.domain.access.rule;

import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.domain.access.AccessQueryable;

/**
 * Interface representing an access rule that can be applied to access requests.
 * <p>
 * Extends {@link AccessQueryable} to allow integration with access query mechanisms.
 * Implementations define custom logic to handle access requests via the {@link #onRequest(AccessRequest)} method.
 * </p>
 */
public interface AccessRule extends AccessQueryable {

    /**
     * Handles the given access request according to the rule's logic.
     *
     * @param request the access request to process
     */
    void onRequest(AccessRequest request);
}
