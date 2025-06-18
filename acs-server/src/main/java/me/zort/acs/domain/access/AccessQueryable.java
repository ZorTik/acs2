package me.zort.acs.domain.access;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccessQueryable {

    /**
     * Returns a paginated {@link Page} of {@link Subject} entities of the specified type
     * that the given accessor has access to, based on the provided list of rights holders.
     *
     * @param accessor the subject-like entity requesting access
     * @param targetSubjectType the type of subjects to be accessed
     * @param rightsHolders the list of rights holders to check access against
     * @param pageable the pagination information
     * @return a {@link Page} containing accessible subjects for the accessor
     */
    default Page<Subject> queryForAccessibleSubjects(
            SubjectLike accessor, SubjectType targetSubjectType, List<RightsHolder> rightsHolders, Pageable pageable) {
        return Page.empty();
    }
}
