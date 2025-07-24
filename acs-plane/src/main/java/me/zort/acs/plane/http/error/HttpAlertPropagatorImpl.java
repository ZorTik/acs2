package me.zort.acs.plane.http.error;

import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.api.http.error.HttpAlertPropagator;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HttpAlertPropagatorImpl implements HttpAlertPropagator {

    @Override
    public void propagateErrorToModel(@Nullable HttpError error, Model model) {
        if (error != null) {
            propagateAlertToModel(error.getMessage(), model);
        }
    }

    @Override
    public void propagateAlertToModel(@Nullable String alert, Model model) {
        if (alert != null) {
            model.addAttribute("alert", alert);
        }
    }
}
