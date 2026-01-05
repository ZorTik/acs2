package me.zort.acs.core.domain.access.request;

import lombok.Getter;
import me.zort.acs.core.domain.access.rights.RightsHolder;
import me.zort.acs.core.model.SubjectLike;

@Getter
public final class SubjectToSubjectAccessRequest extends AccessRequestBase {
    private final SubjectLike accessor;
    private final SubjectLike accessed;
    private final RightsHolder rightsHolder;

    public SubjectToSubjectAccessRequest(SubjectLike accessor, SubjectLike accessed, RightsHolder rightsHolder) {
        this.accessor = accessor;
        this.accessed = accessed;
        this.rightsHolder = rightsHolder;
    }
}
