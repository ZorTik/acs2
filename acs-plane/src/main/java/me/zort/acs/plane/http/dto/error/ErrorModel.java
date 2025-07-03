package me.zort.acs.plane.http.dto.error;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.plane.api.http.error.HttpError;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorModel {
    private int status;
    private String message;

    public ErrorModel(HttpError error) {
        this.status = error.getStatusCode();
        this.message = error.getMessage();
    }

}
