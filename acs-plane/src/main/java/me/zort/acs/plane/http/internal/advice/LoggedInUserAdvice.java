package me.zort.acs.plane.http.internal.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.http.internal.service.PathService;
import me.zort.acs.plane.http.security.LoggedInUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class LoggedInUserAdvice {
    private final PathService pathService;

    @ModelAttribute
    public void addLoggedInUser(
            Model model, @AuthenticationPrincipal LoggedInUserDetails details, HttpServletRequest request) {
        if (details != null
                && details.getUser() != null
                && pathService.getPathGroup(request.getRequestURI()).supportsViewModel()) {
            model.addAttribute("user", details.getUser());
        }
    }
}
