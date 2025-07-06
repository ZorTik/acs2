package me.zort.acs.api.domain.access.request;

import lombok.Getter;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.subject.SubjectLike;

@Getter
public final class SubjectToSubjectAccessRequest implements AccessRequest {
    private final SubjectLike accessor;
    private final SubjectLike accessed;
    private final RightsHolder rightsHolder;

    private boolean granted;

    public SubjectToSubjectAccessRequest(SubjectLike accessor, SubjectLike accessed, RightsHolder rightsHolder) {
        this.accessor = accessor;
        this.accessed = accessed;
        this.rightsHolder = rightsHolder;
        this.granted = false;
    }

    @Override
    public void grant() {
        this.granted = true;
    }
}
