package me.zort.acs.plane.http.error;

import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.api.http.error.HttpErrorPropagator;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HttpErrorPropagatorImpl implements HttpErrorPropagator {

    @Override
    public void propagateErrorToModel(@Nullable HttpError error, Model model) {
        if (error != null) {
            model.addAttribute("error", error.getMessage());
        }
    }
}
