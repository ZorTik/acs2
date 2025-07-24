package me.zort.acs.plane.http.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.Credentials;
import me.zort.acs.plane.api.domain.security.CredentialsService;
import me.zort.acs.plane.api.domain.security.PrivilegesService;
import me.zort.acs.plane.api.domain.security.UserAuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaneUserDetailsService implements UserDetailsService {
    private final UserAuthService userAuthService;
    private final CredentialsService credentialsService;
    private final PrivilegesService privilegesService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthService.getUserByPrincipal(username)
                .map(user -> {
                    Credentials credentials = credentialsService.getCredentialsByUser(user)
                            .orElseThrow(() -> new UsernameNotFoundException("Credentials not found"));

                    return new LoggedInUserDetails(user, credentials, privilegesService.getGrantedPrivileges(user));
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found by provided principal."));
    }
}
