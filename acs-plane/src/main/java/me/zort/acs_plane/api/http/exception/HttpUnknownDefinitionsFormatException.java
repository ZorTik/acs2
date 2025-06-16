package me.zort.acs_plane.api.http.exception;

public class HttpUnknownDefinitionsFormatException extends RuntimeException {
    // TODO: API exception

    public HttpUnknownDefinitionsFormatException(String mimeType) {
        super(String.format("Unknown definitions format for mime type %s", mimeType));
    }
}
