package me.zort.acs.domain.model;

import lombok.Getter;

@Getter
public final class AccessRequest {
    private final SubjectLike accessor;
    private final SubjectLike accessed;
    private final Node node;

    private boolean granted;

    public AccessRequest(SubjectLike accessor, SubjectLike accessed, Node node) {
        this.accessor = accessor;
        this.accessed = accessed;
        this.node = node;
        this.granted = false;
    }

    public void grant() {
        this.granted = true;
    }
}
