package me.zort.acs.plane.api.http.error;

import org.jetbrains.annotations.Nullable;
import org.springframework.ui.Model;

public interface HttpErrorPropagator {

    /**
     * Propagates the given error to the model.
     *
     * @param error the error to propagate
     * @param model the model to propagate the error to
     */
    void propagateErrorToModel(@Nullable HttpError error, Model model);
}
