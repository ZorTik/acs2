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

/**
 * Abstract implementation of {@link UserDetailsService} for integrating with ACS (Access Control Service).
 * <p>
 * This service uses an {@link AcsClientV1} to retrieve user authorities based on the provided username,
 * and delegates the creation of {@link UserDetails} to subclasses via the
 * {@link #loadUserByUsernameAndAuthorities(String, Collection)} method.
 * </p>
 * <p>
 * Subclasses must implement how to construct a {@link UserDetails} instance using the username and granted authorities.
 * </p>
 */
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

    /**
     * Loads the user details for the given username and authorities.
     * This method should be implemented to return a {@link UserDetails} instance
     * using the provided username and granted authorities.
     *
     * @param username the username identifying the user whose data is required
     * @param authorities the authorities granted to the user
     * @return a fully populated user record (never {@code null})
     *
     * @throws UsernameNotFoundException if the user could not be found
     */
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
