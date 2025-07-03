package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.http.dto.error.ErrorModel;
import me.zort.acs.plane.http.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@ControllerAdvice
public class HttpErrorControllerAdvice {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final AbstractJsonHttpMessageConverter messageConverter;

    @ExceptionHandler(HttpError.class)
    public void handleHttpError(HttpError error, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (PATH_MATCHER.match(PathUtils.PANEL_PREFIX, request.getRequestURI())) {
                // Panel path
                request.getRequestDispatcher("/error.html").forward(request, response);
            } else {
                // Api path
                response.setStatus(error.getStatusCode());
                response.setContentType("application/json");
                messageConverter.write(
                        new ErrorModel(error), MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: More robust handling
        }
    }
}
