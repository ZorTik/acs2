package me.zort.acs.domain.grant.type;

import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.domain.grant.GrantBase;
import me.zort.acs.domain.group.Group;
import me.zort.acs.domain.model.Subject;

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
