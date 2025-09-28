package me.zort.acs.plane.event;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.Role;
import me.zort.acs.plane.api.domain.user.UserService;
import me.zort.acs.plane.domain.user.event.UserCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAdminRoleListener {
    private final UserService userService;

    @EventListener
    public void onUserCreated(UserCreatedEvent event) {
        if (userService.getUserCount() == 1) {
            userService.setRole(event.getUser(), Role.ADMIN);
        }
    }
}
