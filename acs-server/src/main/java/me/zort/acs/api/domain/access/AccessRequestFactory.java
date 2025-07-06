package me.zort.acs.api.domain.access;

import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.subject.SubjectLike;
import org.jetbrains.annotations.NotNull;

public interface AccessRequestFactory {

    /**
     * Constructs an AccessRequest object with the given parameters.
     *
     * @param from The accessing object
     * @param to The accessed object
     * @param rightsHolder The rights holder check applicability for
     * @return An AccessRequest object with the given parameters
     */
    @NotNull AccessRequest createAccessRequest(SubjectLike from, SubjectLike to, RightsHolder rightsHolder);
}
