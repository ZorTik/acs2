package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.http.dto.error.ErrorModel;
import me.zort.acs.plane.http.error.exception.PanelNoDefaultRealmException;
import me.zort.acs.plane.http.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@ControllerAdvice
public class HttpErrorControllerAdvice {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final HttpMessageConverter<Object> messageConverter;

    @ExceptionHandler(HttpError.class)
    public ModelAndView handleHttpError(HttpError error, HttpServletRequest request, HttpServletResponse response) {
        try {
            ErrorModel errorModel = new ErrorModel(error);

            // Panel or API error handling
            if (PATH_MATCHER.match(PathUtils.PANEL_PATH_PATTERN, request.getRequestURI())) {
                ModelAndView maw = new ModelAndView();
                maw.setViewName("error");
                maw.addObject("error", errorModel);

                return maw;
            } else {
                response.setStatus(error.getStatusCode());
                response.setContentType("application/json");
                messageConverter.write(errorModel, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setContentLength(0);
        }

        return null;
    }

    @ExceptionHandler(PanelNoDefaultRealmException.class)
    public ModelAndView handlePanelNoDefaultRealmException(
            PanelNoDefaultRealmException e, HttpServletRequest request, HttpServletResponse response) {
        // TODO: Redirectovat na vytvoření realmu
    }
}
