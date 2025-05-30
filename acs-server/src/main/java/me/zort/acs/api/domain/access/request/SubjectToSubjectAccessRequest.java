package me.zort.acs.api.domain.access.request;

import lombok.Getter;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Node;

@Getter
public final class SubjectToSubjectAccessRequest implements AccessRequest {
    private final SubjectLike accessor;
    private final SubjectLike accessed;
    private final Node node;

    private boolean granted;

    public SubjectToSubjectAccessRequest(SubjectLike accessor, SubjectLike accessed, Node node) {
        this.accessor = accessor;
        this.accessed = accessed;
        this.node = node;
        this.granted = false;
    }

    @Override
    public void grant() {
        this.granted = true;
    }
}
