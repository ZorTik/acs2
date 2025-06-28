package me.zort.acs.plane.api.http.exception;

public class HttpRealmNotFoundException extends RuntimeException {

    public HttpRealmNotFoundException(String realmName) {
        super(String.format("Realm %s not found.", realmName));
    }
}
