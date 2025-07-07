package me.zort.acs.plane.http.security;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.ApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class PlaneAuthenticationProvider implements AuthenticationProvider {
    private final ApiKeyService apiKeyService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apikey = authentication.getName();
        if (apiKeyService.isValidApiKey(apikey)) {
            return new UsernamePasswordAuthenticationToken(apikey, null, authentication.getAuthorities());
        }

        throw new BadCredentialsException("Invalid API key");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
