package me.zort.acs.spring.system;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public interface AcsUserDetailsProvider {

    UserDetails getUserDetails(
            String username, Collection<? extends GrantedAuthority> authorities) throws UsernameNotFoundException;
}
