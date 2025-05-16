package me.zort.acs.http.handler;

import me.zort.acs.http.dto.body.BasicResponse;
import me.zort.acs.http.exception.ACSHttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ACSExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ACSHttpException.class)
    public ResponseEntity<BasicResponse> handleACSException(ACSHttpException e) {
        return ResponseEntity.status(e.getCode()).body(new BasicResponse(e.getCode(), e.getMessage()));
    }
}
