package me.zort.acs.plane.api.http.exception;

import me.zort.acs.plane.api.http.error.HttpError;

public class HttpRealmNotFoundException extends HttpError {

    public HttpRealmNotFoundException(String realmName) {
        super(404, String.format("Realm %s not found.", realmName));
    }
}
