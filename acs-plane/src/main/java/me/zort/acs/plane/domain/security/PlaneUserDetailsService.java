package me.zort.acs.plane.domain.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.UserAuthService;
import me.zort.acs.plane.api.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlaneUserDetailsService implements UserDetailsService {
    private final UserAuthService userAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userAuthService.getUserByPrincipal(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found by provided principal."));

        String pwd = userAuthService.getEncodedPassword(user.getId());
        return new PlaneUserDetails(user.getDisplayName(), pwd);
    }
}
