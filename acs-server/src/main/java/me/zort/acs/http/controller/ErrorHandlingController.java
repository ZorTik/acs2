package me.zort.acs.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.zort.acs.http.dto.body.BasicResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlingController extends AbstractErrorController {

    public ErrorHandlingController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    // Route not found
    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicResponse> handleUnknownRequest(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        String message = (String) getErrorAttributes(request, ErrorAttributeOptions.defaults())
                .getOrDefault("message", "Unknown error");

        return ResponseEntity.status(status).body(new BasicResponse(status.value(), 0, message));
    }
}
