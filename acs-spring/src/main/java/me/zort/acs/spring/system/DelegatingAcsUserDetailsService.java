package me.zort.acs.spring.system;

import me.zort.acs.client.v1.AcsClientV1;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class DelegatingAcsUserDetailsService extends AcsUserDetailsService {
    @NotNull
    private final AcsUserDetailsProvider userDetailsProvider;

    public DelegatingAcsUserDetailsService(
            @NotNull AcsClientV1 client,
            @NotNull AcsSystemConfig config, @NotNull AcsUserDetailsProvider userDetailsProvider) {
        super(client, config);
        this.userDetailsProvider = userDetailsProvider;
    }

    @Override
    public UserDetails loadUserByUsernameAndAuthorities(
            String username, Collection<? extends GrantedAuthority> authorities) throws UsernameNotFoundException {
        return userDetailsProvider.getUserDetails(username, authorities);
    }
}
