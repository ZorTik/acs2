package me.zort.acs.plane.http.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorViewControllerAdvice {
    private final ErrorViewer errorViewer;

    @ModelAttribute
    public void addErrorAttributes(Model model, HttpServletRequest request) {
        errorViewer.showRedirectErrors(model, request);
    }
}
