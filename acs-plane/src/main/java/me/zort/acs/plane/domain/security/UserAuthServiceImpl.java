package me.zort.acs.plane.domain.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.CredentialsService;
import me.zort.acs.plane.api.domain.security.UserAuthService;
import me.zort.acs.plane.api.domain.user.User;
import me.zort.acs.plane.api.domain.user.UserService;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAuthServiceImpl implements UserAuthService {
    private final UserService userService;
    private final CredentialsService credentialsService;

    @Override
    public @Nullable Optional<? extends User> getUserByPrincipal(String principal) {
        return credentialsService
                .getCredentialsByUsername(principal)
                .flatMap(credentials -> userService.getUserById(credentials.getUserId()));
    }
}
