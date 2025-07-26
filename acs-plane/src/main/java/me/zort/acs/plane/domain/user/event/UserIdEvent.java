package me.zort.acs.plane.domain.user.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.user.User;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserIdEvent {
    private final UUID userId;

    public UserIdEvent(User user) {
        this.userId = user.getId();
    }

}
