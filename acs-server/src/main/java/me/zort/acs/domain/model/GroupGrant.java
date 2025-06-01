package me.zort.acs.domain.model;

import me.zort.acs.api.domain.access.RightsHolder;

import java.util.UUID;

public class GroupGrant extends GrantBase {
    private final Group group;

    public GroupGrant(UUID id, Subject holder, Subject on, Group group) {
        super(id, holder, on);
        this.group = group;
    }

    @Override
    public RightsHolder getRightsHolder() {
        return group;
    }
}
