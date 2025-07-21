package me.zort.acs.plane.http.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.user.UserService;
import me.zort.acs.plane.http.internal.service.PathService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PlaneAuthEntryPoint implements AuthenticationEntryPoint {
    private final UserService userService;
    private final PathService pathService;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (userService.getUserCount() == 0) {
            response.sendRedirect(pathService.getRegisterPage());
        } else {
            response.sendRedirect(pathService.getLoginPage());
        }
    }
}
