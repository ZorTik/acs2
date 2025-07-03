package me.zort.acs.plane.http.internal;

import me.zort.acs.plane.api.http.error.HttpError;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@Service
public class WebRequestService {

    public String getRequiredParameter(WebRequest request, String parameterName) {
        return Optional
                .ofNullable(request.getParameter(parameterName))
                .orElseThrow(() -> new HttpError(400, "MMissing required parameter '" + parameterName + "'."));
    }
}
