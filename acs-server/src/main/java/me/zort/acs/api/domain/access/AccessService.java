package me.zort.acs.api.domain.access;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
    Page<Subject> getAccessibleSubjects(
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
}
