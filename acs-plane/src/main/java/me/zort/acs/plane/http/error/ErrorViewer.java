package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.http.error.HttpAlertPropagator;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ErrorViewer {
    private final HttpAlertPropagator alertPropagator;

    /**
     * Builds a redirect URL with an appended error query parameter.
     *
     * @param url the base URL to redirect to
     * @param type the error type to append as a query parameter
     * @return the constructed redirect URL
     */
    public String buildRedirectErrorView(String url, ErrorType type, Object... args) {
        try {
            String query = new URI(url).getQuery();
            String separator = (query == null || query.isEmpty()) ? "?" : "&";

            url = url + separator + "error=" + URLEncoder.encode(type.name().toLowerCase(), StandardCharsets.UTF_8);
            url = appendErrorArgs(url, args);

            return "redirect:" + url;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URL: " + url, e);
        }
    }

    /**
     * Shows redirect errors based on the provided mappings.
     *
     * @param model the model to propagate alerts to
     * @param request the HTTP servlet request
     */
    public void showRedirectErrors(Model model, HttpServletRequest request) {
        String errorType = request.getParameter("error");
        if (errorType == null || errorType.isEmpty()) {
            return;
        }

        ErrorType errorTypeEnum;
        try {
            errorTypeEnum = ErrorType.valueOf(errorType.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            // Ignoring unknown error types

            return;
        }

        String[] args = request.getParameterValues("args");
        if (args == null) {
            args = new String[0];
        }

        alertPropagator.propagateAlertToModel(errorTypeEnum.getMessage((Object[]) args), model);
    }

    private static String appendErrorArgs(String url, Object[] args) {
        if (args == null || args.length == 0) {
            return url;
        }

        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            if (!sb.isEmpty()) {
                sb.append(",");
            }

            sb.append(arg.toString());
        }
        return url + "&args=" + URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8);
    }
}
