package me.zort.acs.client.http.exception;

public class NodeNotFoundException extends AcsKnownException {

    public NodeNotFoundException(String message) {
        super(message, 1001);
    }
}
