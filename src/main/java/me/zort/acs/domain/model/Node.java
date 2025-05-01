package me.zort.acs.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Node {
    @Getter
    private final String value;

    public boolean isParentOf(Node node) {
        // TODO
    }

    public boolean isLeafNode() {
        return !value.endsWith("*");
    }
}
