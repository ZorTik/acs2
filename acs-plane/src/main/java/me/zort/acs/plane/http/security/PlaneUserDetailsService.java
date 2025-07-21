package me.zort.acs.plane.http.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.credentials.Credentials;
import me.zort.acs.plane.api.domain.credentials.CredentialsService;
import me.zort.acs.plane.api.domain.security.UserAuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaneUserDetailsService implements UserDetailsService {
    private final UserAuthService userAuthService;
    private final CredentialsService credentialsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthService.getUserByPrincipal(username)
                .map(user -> {
                    Credentials credentials = credentialsService.getCredentialsByUserId(user.getId())
                            .orElseThrow(() -> new UsernameNotFoundException("Credentials not found"));

                    return new PlaneUserDetails(user, credentials, List.of()); // TODO: Authorities
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found by provided principal."));
    }
}
