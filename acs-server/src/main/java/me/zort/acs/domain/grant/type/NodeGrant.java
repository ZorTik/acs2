package me.zort.acs.domain.grant.type;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.grant.GrantBase;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;

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
