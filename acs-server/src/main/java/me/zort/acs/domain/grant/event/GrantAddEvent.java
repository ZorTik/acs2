package me.zort.acs.domain.grant.event;

import me.zort.acs.api.domain.model.Grant;

public class GrantAddEvent extends GrantEvent {

    public GrantAddEvent(Grant grant) {
        super(grant);
    }
}
