package me.zort.acs.plane.http.controller.panel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.plane.api.domain.security.AuthService;
import me.zort.acs.plane.api.facade.AuthFacade;
import me.zort.acs.plane.api.http.error.HttpAlertPropagator;
import me.zort.acs.plane.http.facade.util.Result;
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
    private final AuthService authService;
    private final HttpAlertPropagator errorPropagator;

    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("registrationsAllowed", authService.isRegistrationAllowed());

        return "panel/auth/login";
    }

    @GetMapping("/register")
    public String registerGet() {
        if (authService.isRegistrationAllowed()) {
            return "panel/auth/register";
        } else {
            log.warn("User attempted to access registration page, but registrations are not allowed.");

            return "redirect:" + pathService.getLoginPage();
        }
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute @Valid RegisterForm form, Model model, HttpServletRequest request) {
        Result<Void> result = authFacade.register(form);
        if (result.isOk()) {
            result = authFacade.forceLogin(form.getUsername(), form.getPassword(), request);
        }

        if (result.isOk()) {
            return "redirect:" + pathService.getPanelLandingPage();
        } else {
            errorPropagator.propagateErrorToModel(result.getError(), model);

            return "panel/auth/register";
        }
    }
}
