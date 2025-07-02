package me.zort.acs.plane.api.http.exception;

import me.zort.acs.plane.api.http.error.HttpError;

public class HttpUnknownDefinitionsFormatException extends HttpError {

    public HttpUnknownDefinitionsFormatException(String mimeType) {
        super(400, String.format("Unknown definitions format for mime type %s", mimeType));
    }
}
