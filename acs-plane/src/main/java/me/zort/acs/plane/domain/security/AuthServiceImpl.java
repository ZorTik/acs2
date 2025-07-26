package me.zort.acs.plane.domain.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.AuthService;
import me.zort.acs.plane.api.domain.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;

    @Override
    public boolean isRegistrationAllowed() {
        return userService.getUserCount() == 0;
    }
}
