package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.http.dto.error.ErrorModel;
import me.zort.acs.plane.http.error.exception.PanelNoDefaultRealmException;
import me.zort.acs.plane.http.internal.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@ControllerAdvice
public class HttpErrorControllerAdvice {
    private final HttpMessageConverter<Object> messageConverter;
    private final PathService pathService;

    /**
     * Handles HttpError exceptions and returns an appropriate response based on the request path group.
     *
     * @param e the Exception to handle (possibly an HttpError)
     * @param request the request
     * @param response the response
     * @return a ModelAndView for panel errors, or null for API errors
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleHttpError(Exception e, HttpServletRequest request, HttpServletResponse response) {
        HttpError error;
        if (e instanceof HttpError httpError) {
            error = httpError;
        } else {
            error = new HttpError(500, "Internal Server Error");

            log.error("Unhandled exception occurred", e);
        }

        try {
            ErrorModel errorModel = new ErrorModel(error);

            // Panel or API error handling
            switch (pathService.getPathGroup(request.getRequestURI())) {
                case PANEL:
                    ModelAndView maw = new ModelAndView();
                    maw.setViewName("error");
                    maw.addObject("error", errorModel);

                    return maw;
                case API:
                    response.setStatus(error.getStatusCode());
                    response.setContentType("application/json");
                    messageConverter.write(
                            errorModel, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
            }
        } catch (Exception e1) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setContentLength(0);
        }

        return null;
    }

    /**
     * Handles PanelNoDefaultRealmException and redirects to the realm creation page.
     * This should occur only in the panel endpoints.
     *
     * @return a redirect to the realm creation page
     */
    @ExceptionHandler(PanelNoDefaultRealmException.class)
    public String handlePanelNoDefaultRealmException() {
        return "redirect:/panel/realms/create";
    }
}
