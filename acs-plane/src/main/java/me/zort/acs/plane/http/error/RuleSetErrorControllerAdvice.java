package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.ruleset.exception.MalformedRuleSetDataException;
import me.zort.acs.plane.api.http.error.HttpAlertPropagator;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class RuleSetErrorControllerAdvice {
    private final HttpAlertPropagator alertPropagator;

    @ExceptionHandler(MalformedRuleSetDataException.class)
    public String handleMalformedRuleSetDataError(MalformedRuleSetDataException e, HttpServletRequest request, Model model) {
        alertPropagator.propagateAlertToModel("Malformed rule set: " + e.getMessage(), model);

        String redirectPrefix = "redirect:/panel/realms";
        String realmId = request.getParameter("realmId");
        if (realmId != null) {
            redirectPrefix += "/edit?realm=" + realmId;
        }

        return redirectPrefix;
    }
}
