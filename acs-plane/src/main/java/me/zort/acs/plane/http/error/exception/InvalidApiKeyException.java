package me.zort.acs.plane.http.error.exception;

import me.zort.acs.plane.api.http.error.HttpError;

public class InvalidApiKeyException extends HttpError {

    public InvalidApiKeyException() {
        super(401, "Invalid API key");
    }
}
