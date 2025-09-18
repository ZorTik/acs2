package me.zort.acs.plane.domain.user.event;

import lombok.Getter;
import me.zort.acs.plane.api.domain.user.User;

@Getter
public class UserEvent extends UserIdEvent {
    private final User user;

    public UserEvent(User user) {
        super(user);
        this.user = user;
    }
}
