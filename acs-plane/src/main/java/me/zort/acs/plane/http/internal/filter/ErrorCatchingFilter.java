package me.zort.acs.plane.http.internal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.http.error.HttpErrorControllerAdvice;
import me.zort.acs.plane.http.internal.service.PathService;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This one catches all error that occur in filter chain when calling API
 * routes and redirect them to external handler.
 */
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ErrorCatchingFilter extends OncePerRequestFilter {
    private final HttpErrorControllerAdvice errorControllerAdvice;
    private final PathService pathService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            if (pathService.getPathGroup(request.getRequestURI()).supportsViewModel()) {
                // If this one is not an API route, we should not skip the error
                throw e;
            }

            errorControllerAdvice.handleHttpError(e, request, response);
        }
    }
}
