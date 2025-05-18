package me.zort.acs.domain.event;

import me.zort.acs.domain.model.Grant;

public class GrantAddEvent extends GrantEvent {

    public GrantAddEvent(Grant grant) {
        super(grant);
    }
}
