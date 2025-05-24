package me.zort.acs.http.handler;

import lombok.RequiredArgsConstructor;
import me.zort.acs.http.dto.body.BasicResponse;
import me.zort.acs.http.exception.ACSHttpException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@ControllerAdvice
public class AcsExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NotNull HttpMessageNotReadableException e,
            @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        return ResponseEntity.status(400).body(new BasicResponse(400, "Invalid request body"));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NotNull HttpRequestMethodNotSupportedException e,
            @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        return ResponseEntity.status(405).body(new BasicResponse(405, "Method not supported"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException e,
            @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> messageSource.getMessage(fieldError, request.getLocale())));

        BasicResponse responseBody = new BasicResponse(405, "Invalid request format", Map.of(
                "errors", errors));
        return ResponseEntity.status(400).body(responseBody);
    }

    @ExceptionHandler(ACSHttpException.class)
    public ResponseEntity<BasicResponse> handleACSException(ACSHttpException e) {
        return ResponseEntity.status(e.getCode()).body(new BasicResponse(e.getCode(), e.getMessage()));
    }
}
