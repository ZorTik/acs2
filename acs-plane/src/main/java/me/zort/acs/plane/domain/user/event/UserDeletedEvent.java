package me.zort.acs.plane.domain.user.event;

import java.util.UUID;

public class UserDeletedEvent extends UserIdEvent {

    public UserDeletedEvent(UUID userId) {
        super(userId);
    }
}
