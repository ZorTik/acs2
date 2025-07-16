package me.zort.acs.plane.http.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.user.UserService;
import me.zort.acs.plane.http.internal.service.PathService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaneAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final UserService userService;
    private final PathService pathService;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        PathService.PathGroup pathGroup = pathService.getPathGroup(request.getRequestURI());
        if (pathGroup.supportsViewModel() && userService.getUserCount() == 0) {
            // If the request is for a view model and there are no users, redirect to the setup page
            response.sendRedirect(request.getContextPath() + pathService.getSetupPage());
            return;
        }

        if (pathGroup.supportsViewModel()) {
            // If the request is for a view model, redirect to the login page
            response.sendRedirect(request.getContextPath() + pathService.getLoginPage() + "?error=true");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
