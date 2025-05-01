package me.zort.acs.domain;

import lombok.Getter;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

@Getter
public final class AccessRequest {
    private final Subject accessor;
    private final Subject accessed;
    private final Node node;

    private boolean granted;

    public AccessRequest(Subject accessor, Subject accessed, Node node) {
        this.accessor = accessor;
        this.accessed = accessed;
        this.node = node;
        this.granted = false;
    }

    public void grant() {
        this.granted = true;
    }
}
