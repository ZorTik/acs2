package me.zort.acs.spring;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

/**
 * Provider interface for obtaining {@link UserDetails} by username.
 * Called by automatic configuration from {@code DelegatingAcsUserDetailsService}.
 */
public interface AcsUserDetailsProvider {

    /**
     * Returns {@link UserDetails} for the given username and authorities.
     *
     * @param username The username to look up
     * @param authorities The authorities to assign to the user
     * @return The user details
     *
     * @throws UsernameNotFoundException if the user was not found
     */
    UserDetails getUserDetails(
            String username, Collection<? extends GrantedAuthority> authorities) throws UsernameNotFoundException;
}
