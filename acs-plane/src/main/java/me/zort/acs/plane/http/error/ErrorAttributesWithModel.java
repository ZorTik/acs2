package me.zort.acs.plane.http.error;

import jakarta.servlet.RequestDispatcher;
import me.zort.acs.plane.http.dto.error.ErrorModel;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class ErrorAttributesWithModel extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> attributes = super.getErrorAttributes(webRequest, options);

        Integer statusCode = (Integer) webRequest.getAttribute(
                RequestDispatcher.ERROR_STATUS_CODE, WebRequest.SCOPE_REQUEST);
        if (statusCode == null) {
            statusCode = 500;
        }

        String message = getCommonErrorMessages(statusCode);

        Throwable error = getError(webRequest);
        if (error != null && message == null) {
            message = error.getMessage();
        }

        attributes.put("error", new ErrorModel(statusCode, message));
        return attributes;
    }

    private static @Nullable String getCommonErrorMessages(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }

        switch (statusCode) {
            case 401:
            case 403:
                return "Unauthorized.";
        }
        return null;
    }
}
