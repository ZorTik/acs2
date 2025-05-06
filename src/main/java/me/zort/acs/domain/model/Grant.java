package me.zort.acs.domain.model;

import lombok.Getter;

public class Grant {
    private final Subject holder;
    private final Subject on;
    @Getter
    private final Node node;

    public Grant(Subject holder, Subject on, Node node) {
        this.holder = holder;
        this.on = on;
        this.node = node;
    }

    public boolean appliesTo(Node node, String delimiter) {
        return this.node.isParentOf(node, delimiter);
    }

    public Subject getAccessor() {
        return holder;
    }

    public Subject getAccessed() {
        return on;
    }
}
