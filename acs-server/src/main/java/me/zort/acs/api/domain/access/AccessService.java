package me.zort.acs.api.domain.access;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * A general access service.
 */
public interface AccessService {

    /**
     * Returns a paginated list of {@link Subject} entities of the given type
     * that the specified accessor has access to, based on the provided rights holders.
     *
     * @param accessor the subject-like entity requesting access
     * @param targetSubjectType the type of subjects to be accessed
     * @param rightsHolders the list of rights holders to check access against
     * @param pageable the pagination information
     * @return a list of accessible subjects for the accessor
     */
    Page<? extends SubjectLike> getAccessibleSubjects(
            SubjectLike accessor, SubjectType targetSubjectType, List<RightsHolder> rightsHolders, Pageable pageable);

    /**
     * Determines whether the given accessor has access to the specified accessed subject
     * based on the provided rights holder.
     *
     * @param accessor the subject-like entity requesting access
     * @param accessed the subject-like entity to be accessed
     * @param rightsHolder the rights holder used to check access permissions
     * @return {@code true} if the accessor has access to the accessed subject, {@code false} otherwise
     * @throws IllegalArgumentException if the request is invalid
     */
    boolean hasAccess(SubjectLike accessor, SubjectLike accessed, RightsHolder rightsHolder) throws IllegalArgumentException;

    /**
     * Returns ALL nodes that are assignable to accessed's subject type and their
     * grant states in relation with accessor.
     *
     * @param accessor The accessor subject
     * @param accessed The accessed subject
     * @return The nodes and their states
     */
    Map<Node, Boolean> getGrantStatesBetween(SubjectLike accessor, SubjectLike accessed);
}
