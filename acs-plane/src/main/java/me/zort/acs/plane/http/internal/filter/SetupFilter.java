package me.zort.acs.plane.http.internal.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.setup.SetupService;
import me.zort.acs.plane.http.internal.service.PathService;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(1)
@Component
@RequiredArgsConstructor
public class SetupFilter extends OncePerRequestFilter {
    private final SetupService setupService;
    private final PathService pathService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        boolean isPanelRequest = pathService.getPathGroup(request.getRequestURI()) != PathService.PathGroup.PANEL;
        if (!isPanelRequest) {
            // If the request is not for the panel, we can skip the setup check
            filterChain.doFilter(request, response);
            return;
        }

        if (setupService.isSetupComplete()) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(pathService.getSetupPage());
        }
    }
}
