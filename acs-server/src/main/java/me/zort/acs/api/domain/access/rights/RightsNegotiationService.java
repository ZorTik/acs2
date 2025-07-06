package me.zort.acs.api.domain.access.rights;

import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for negotiating rights between two parties
 * in the access control system.
 *
 * <p>This service is responsible for determining which entities (rights holders)
 * provide access from one subject (the accessor) to another subject (the accessed).</p>
 */
public interface RightsNegotiationService {

    /**
     * Retrieves a list of {@link RightsHolder} instances that represent
     * the source of access rights between the accessor and the accessed subject.
     * <p></p>
     * Note that the accessor and accessed subjects may be of {@link me.zort.acs.domain.model.NullSubject} type,
     * in that case, only the default rights holders should be returned.
     *
     * @param accessor the subject attempting to access resources
     * @param accessed the subject being accessed
     * @return a list of {@link RightsHolder} objects representing the entities
     *         that grant access rights in this context
     */
    List<RightsHolder> getRightsHolders(SubjectLike accessor, SubjectLike accessed);

    Page<Subject> getCandidateSubjects(
            SubjectLike accessor, SubjectType accessedType, List<RightsHolder> anyOf, Pageable pageable);
}
