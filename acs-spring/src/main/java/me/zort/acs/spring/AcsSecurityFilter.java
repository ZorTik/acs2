package me.zort.acs.spring;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.client.AcsNodeResolvable;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.v1.AcsClientV1;
import me.zort.acs.spring.exception.AcsInvalidPrincipalException;
import me.zort.acs.spring.exception.AcsUnauthenticatedException;
import me.zort.acs.spring.exception.AcsUnauthorizedException;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class AcsSecurityFilter extends OncePerRequestFilter {
    private final AcsClientV1 client;
    private final AccessTargetResolver targetResolver;

    @Override
    protected void doFilterInternal(
            final @NotNull HttpServletRequest request,
            final @NotNull HttpServletResponse response,
            final @NotNull FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AcsUnauthenticatedException();
        }

        if (authentication.getPrincipal() instanceof AcsSubjectResolvable accessor) {
            AcsSubjectResolvable accessed = targetResolver.resolveAccessedSubject(request);
            Set<AcsNodeResolvable> nodes = targetResolver.resolveNodesToCheck(request, accessor, accessed);

            if (!client.checkAccess(accessor, accessed, nodes).all()) {
                throw new AcsUnauthorizedException(accessor, accessed, nodes);
            }

            filterChain.doFilter(request, response);
        } else {
            throw new AcsInvalidPrincipalException(
                    authentication.getPrincipal(), "Principal must inherit AcsSubjectResolvable");
        }
    }
}
