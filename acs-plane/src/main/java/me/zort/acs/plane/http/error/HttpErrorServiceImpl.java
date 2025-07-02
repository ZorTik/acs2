package me.zort.acs.plane.http.error;

import me.zort.acs.plane.api.http.error.HttpError;
import me.zort.acs.plane.api.http.error.HttpErrorService;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class HttpErrorServiceImpl implements HttpErrorService {

    @Override
    public void propagate(@Nullable HttpError error) {
        // TODO: Throw API exception
    }

    @Override
    public void propagate(@Nullable HttpError error, Model model) {
        // TODO: Propagate to the model as parameter and then render it as overlay in the view
    }
}
