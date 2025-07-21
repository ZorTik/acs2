package me.zort.acs.plane.domain.user.event;

import me.zort.acs.plane.api.domain.user.User;

public class UserDeletedEvent extends UserEvent {

    public UserDeletedEvent(User user) {
        super(user);
    }
}
