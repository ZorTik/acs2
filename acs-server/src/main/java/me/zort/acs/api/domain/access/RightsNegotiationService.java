package me.zort.acs.api.domain.access;

import me.zort.acs.domain.model.Subject;

import java.util.List;

/**
 * Service interface for negotiating rights between two subjects
 * in the access control system.
 *
 * <p>This service is responsible for determining which entities (rights holders)
 * provide access from one subject (the accessor) to another subject (the accessed).</p>
 */
public interface RightsNegotiationService {

    /**
     * Retrieves a list of {@link RightsHolder} instances that represent
     * the source of access rights between the accessor and the accessed subject.
     *
     * @param accessor the subject attempting to access resources
     * @param accessed the subject being accessed
     * @return a list of {@link RightsHolder} objects representing the entities
     *         that grant access rights in this context
     */
    List<RightsHolder> getRightsHolders(Subject accessor, Subject accessed);
}
