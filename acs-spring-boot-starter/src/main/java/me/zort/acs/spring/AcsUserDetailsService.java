package me.zort.acs.spring;

import me.zort.acs.client.http.model.Subject;
import me.zort.acs.client.v1.AcsClientV1;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AcsUserDetailsService implements UserDetailsService {
    private final AcsClientV1 client;
    private final Subject systemSubject;
    private final String userSubjectType;

    public AcsUserDetailsService(
            @NotNull AcsClientV1 client, @NotNull Subject systemSubject, String userSubjectType) {
        this.client = Objects.requireNonNull(client, "Client cannot be null");
        this.systemSubject = Objects.requireNonNull(systemSubject, "System subject supplier cannot be null");
        this.userSubjectType = userSubjectType;
    }

    public abstract UserDetails loadUserByUsernameAndAuthorities(
            String username, Collection<? extends GrantedAuthority> authorities) throws UsernameNotFoundException;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Subject userSubject = Subject.of(userSubjectType, username);

        Collection<? extends GrantedAuthority> authorities = client.listNodesWithGrantState(userSubject, systemSubject)
                .getNodesByState(true)
                .stream()
                .map(node -> new SimpleGrantedAuthority(node.getValue()))
                .collect(Collectors.toSet());

        return loadUserByUsernameAndAuthorities(username, authorities);
    }
}
