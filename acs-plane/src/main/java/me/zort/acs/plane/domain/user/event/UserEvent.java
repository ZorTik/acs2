package me.zort.acs.plane.domain.user.event;

import me.zort.acs.plane.api.domain.user.User;

public class UserEvent extends UserIdEvent {

    public UserEvent(User user) {
        super(user);
    }
}
