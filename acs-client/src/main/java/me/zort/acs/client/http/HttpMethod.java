package me.zort.acs.client.http;

public enum HttpMethod {
    GET,
    POST,
    PUT;

    public boolean supportsBody() {
        return this != GET;
    }
}
