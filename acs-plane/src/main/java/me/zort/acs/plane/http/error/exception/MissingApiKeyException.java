package me.zort.acs.plane.http.error.exception;

import me.zort.acs.plane.api.http.error.HttpError;

public class MissingApiKeyException extends HttpError {

    public MissingApiKeyException() {
        super(401, "Missing API key");
    }
}
