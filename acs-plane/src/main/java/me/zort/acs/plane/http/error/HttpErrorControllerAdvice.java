package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.http.dto.error.ErrorModel;
import me.zort.acs.plane.http.error.exception.PanelNoDefaultRealmException;
import me.zort.acs.plane.http.internal.service.PathService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
            error = mapKnownHttpError(e);

            if (error == null) {
                error = new HttpError(500, "Internal Server Error");

                log.error("Unhandled exception occurred", e);
            }
        }

        try {
            ErrorModel errorModel = new ErrorModel(error);

            // Panel or API error handling
            switch (pathService.getPathGroup(request.getRequestURI())) {
                case API:
                    response.setStatus(error.getStatusCode());
                    response.setContentType("application/json");
                    messageConverter.write(
                            errorModel, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
                case PANEL:
                default:
                    ModelAndView maw = new ModelAndView();
                    maw.setViewName("error");
                    maw.addObject("error", errorModel);

                    return maw;
            }
        } catch (Exception e1) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setContentLength(0);
        }

        return null;
    }

    /**
     * Maps known exceptions to HttpError objects.
     *
     * @param e the exception to map
     * @return an HttpError if the exception is known, or null if it is not
     */
    private @Nullable HttpError mapKnownHttpError(Exception e) {
        Class<? extends Exception> type = e.getClass();
        if (type.equals(NoResourceFoundException.class)) {
            return new HttpError(404, "Resource Not Found");
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
