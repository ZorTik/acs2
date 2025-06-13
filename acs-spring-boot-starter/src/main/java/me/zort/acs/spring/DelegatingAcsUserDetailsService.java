package me.zort.acs.spring;

import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.core.domain.SubjectProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Objects;

public class DelegatingAcsUserDetailsService extends AcsUserDetailsService {
    @NotNull
    private final AcsUserDetailsProvider userDetailsProvider;

    public DelegatingAcsUserDetailsService(
            @NotNull AcsClientV1 client,
            @NotNull SubjectProvider systemSubjectProvider,
            @NotNull String userSubjectType, @NotNull AcsUserDetailsProvider userDetailsProvider) {
        super(client, systemSubjectProvider, userSubjectType);
        this.userDetailsProvider = Objects.requireNonNull(
                userDetailsProvider,  "userDetailsProvider cannot be null");
    }

    @Override
    public UserDetails loadUserByUsernameAndAuthorities(
            String username, Collection<? extends GrantedAuthority> authorities) throws UsernameNotFoundException {
        return userDetailsProvider.getUserDetails(username, authorities);
    }
}
