package me.zort.acs.domain.grant.event;

import me.zort.acs.api.domain.model.Grant;

public class GrantRemoveEvent extends GrantEvent {

    public GrantRemoveEvent(Grant grant) {
        super(grant, true);
    }
}
