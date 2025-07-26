package me.zort.acs.plane.http.security;

import lombok.Getter;
import me.zort.acs.plane.api.domain.security.Credentials;
import me.zort.acs.plane.api.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class LoggedInUserDetails implements UserDetails {
    private final User user;
    private final Credentials credentials;
    private final Collection<? extends GrantedAuthority> authorities;

    public LoggedInUserDetails(User user, Credentials credentials, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.credentials = credentials;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return credentials.getUsername();
    }

    @Override
    public String getPassword() {
        return credentials.getHashedPassword();
    }
}
