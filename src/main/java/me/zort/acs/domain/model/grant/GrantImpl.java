package me.zort.acs.domain.model.grant;

import lombok.Getter;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

public class GrantImpl implements Grant {
    private final Subject holder;
    private final Subject on;
    @Getter
    private final Node node;
    private final String delimiter;

    public GrantImpl(Subject holder, Subject on, Node node, String delimiter) {
        this.holder = holder;
        this.on = on;
        this.node = node;
        this.delimiter = delimiter;
    }

    public boolean appliesTo(Node node) {
        return this.node.isParentOf(node, delimiter);
    }

    public Subject getAccessor() {
        return holder;
    }

    public Subject getAccessed() {
        return on;
    }
}
