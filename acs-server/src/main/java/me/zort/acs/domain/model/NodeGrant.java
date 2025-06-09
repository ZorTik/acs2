package me.zort.acs.domain.model;

import me.zort.acs.api.domain.access.rights.RightsHolder;

import java.util.UUID;

public class NodeGrant extends GrantBase {
    private final Node node;

    public NodeGrant(UUID id, Subject holder, Subject on, Node node) {
        super(id, holder, on);
        this.node = node;
    }

    @Override
    public RightsHolder getRightsHolder() {
        return node;
    }
}
