package me.zort.acs.domain.model;

import lombok.Getter;
import me.zort.acs.api.domain.access.AccessRequest;
import me.zort.acs.api.domain.model.SubjectLike;

@Getter
public final class DefaultAccessRequest implements AccessRequest {
    private final SubjectLike accessor;
    private final SubjectLike accessed;
    private final Node node;

    private boolean granted;

    public DefaultAccessRequest(SubjectLike accessor, SubjectLike accessed, Node node) {
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
