package me.zort.acs.plane.http.internal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.ApiKey;
import me.zort.acs.plane.api.domain.security.ApiKeyService;
import me.zort.acs.plane.http.error.exception.InvalidApiKeyException;
import me.zort.acs.plane.http.error.exception.MissingApiKeyException;
import me.zort.acs.plane.http.internal.service.PathService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ApiKeyFilter extends OncePerRequestFilter {
    private final PathService pathService;
    private final ApiKeyService apiKeyService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (pathService.getPathGroup(request.getRequestURI()).equals(PathService.PathGroup.API)) {
            String apiKey = request.getHeader("API-Key");
            if (apiKey == null) {
                throw new MissingApiKeyException();
            }

            ApiKey key = apiKeyService.verifyApiKey(apiKey).orElseThrow(InvalidApiKeyException::new);
            Authentication authentication = new UsernamePasswordAuthenticationToken(key, null, key.getClaims());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
