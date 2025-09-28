package me.zort.acs.plane.http.internal.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.http.internal.service.PathService;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class NavAdvice {
    private final PathService pathService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @ModelAttribute
    public void addNavIdentifier(Model model, HttpServletRequest request) {
        if (pathService.getPathGroup(request.getRequestURI()) != PathService.PathGroup.PANEL) {
            return;
        }

        String path = request.getRequestURI();

        String identifier = "";
        if (path.equals("/panel")) {
            identifier = "home";
        } else if (pathMatcher.match("/panel/realms/**", path)) {
            identifier = "realms";
        } else if (pathMatcher.match("/panel/keys/**", path)) {
            identifier = "keys";
        }

        model.addAttribute("navIdentifier", identifier);
    }
}
