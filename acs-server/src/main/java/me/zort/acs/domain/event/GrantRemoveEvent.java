package me.zort.acs.domain.event;

import me.zort.acs.domain.model.Grant;

public class GrantRemoveEvent extends GrantEvent {

    public GrantRemoveEvent(Grant grant) {
        super(grant, true);
    }
}
