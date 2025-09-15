package me.zort.acs.plane.domain.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.RegistrationsStrategy;
import me.zort.acs.plane.api.domain.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationsStrategyImpl implements RegistrationsStrategy {
    private final UserService userService;

    @Override
    public boolean isRegistrationAllowed() {
        return userService.getUserCount() == 0;
    }
}
