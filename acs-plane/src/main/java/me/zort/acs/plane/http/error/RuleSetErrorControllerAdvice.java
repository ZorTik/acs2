package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.ruleset.exception.InvalidRuleSetException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(0)
@ControllerAdvice
@RequiredArgsConstructor
public class RuleSetErrorControllerAdvice {
    private final ErrorViewer errorViewer;

    @ExceptionHandler(InvalidRuleSetException.class)
    public String handleMalformedRuleSetDataError(InvalidRuleSetException e, HttpServletRequest request) {
        String redirectUrl = "/panel/realms";
        String realmId = request.getParameter("realm");
        if (realmId != null) {
            redirectUrl += "/edit?realm=" + realmId;
        }

        return errorViewer.buildRedirectErrorView(redirectUrl, ErrorType.MALFORMED_RULESET, e.getMessage());
    }
}
