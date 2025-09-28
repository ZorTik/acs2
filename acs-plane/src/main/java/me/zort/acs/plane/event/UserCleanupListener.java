package me.zort.acs.plane.event;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.CredentialsService;
import me.zort.acs.plane.domain.user.event.UserDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserCleanupListener {
    private final CredentialsService credentialsService;

    @EventListener
    public void onUserDeleted(UserDeletedEvent event) {
        credentialsService.deleteCredentialsByUser(event.getUser());
    }
}
