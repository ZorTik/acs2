package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.ruleset.exception.MalformedRuleSetDataException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(0)
@ControllerAdvice
@RequiredArgsConstructor
public class RuleSetErrorControllerAdvice {
    private final ErrorViewer errorViewer;

    @ExceptionHandler(MalformedRuleSetDataException.class)
    public String handleMalformedRuleSetDataError(MalformedRuleSetDataException e, HttpServletRequest request) {
        String redirectUrl = "/panel/realms";
        String realmId = request.getParameter("realm");
        if (realmId != null) {
            redirectUrl += "/edit?realm=" + realmId;
        }

        return errorViewer.buildRedirectErrorView(redirectUrl, ErrorType.MALFORMED_RULESET, e.getMessage());
    }
}
