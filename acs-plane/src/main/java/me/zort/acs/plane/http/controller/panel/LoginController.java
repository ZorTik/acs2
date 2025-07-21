package me.zort.acs.plane.http.controller.panel;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.plane.api.facade.AuthFacade;
import me.zort.acs.plane.api.http.error.HttpErrorPropagator;
import me.zort.acs.plane.facade.util.Result;
import me.zort.acs.plane.http.dto.auth.RegisterForm;
import me.zort.acs.plane.http.internal.service.PathService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/panel/auth")
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final AuthFacade authFacade;
    private final PathService pathService;
    private final HttpErrorPropagator errorPropagator;

    @GetMapping("/login")
    public String loginGet() {
        return "panel/auth/login";
    }

    @GetMapping("/register")
    public String registerGet() {
        return "panel/auth/register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute @Valid RegisterForm form, Model model, HttpServletRequest request) {
        Result<Void> result = authFacade.register(form);
        if (result.isOk()) {
            try {
                request.login(form.getUsername(), form.getPassword());
            } catch (ServletException e) {
                log.error("Failed to login user after registration.", e);

                result = Result.error(403, "Failed to log in after registration.");
            }
        }

        if (result.isOk()) {
            return "redirect:" + pathService.getPanelLandingPage();
        } else {
            errorPropagator.propagateErrorToModel(result.getError(), model);

            return "panel/auth/register";
        }
    }
}
