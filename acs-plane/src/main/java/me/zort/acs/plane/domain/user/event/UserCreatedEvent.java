package me.zort.acs.plane.domain.user.event;

import me.zort.acs.plane.api.domain.user.User;

public class UserCreatedEvent extends UserEvent {

    public UserCreatedEvent(User user) {
        super(user);
    }
}
