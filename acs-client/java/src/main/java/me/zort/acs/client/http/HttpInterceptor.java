package me.zort.acs.client.http;

public interface HttpInterceptor {

    default void beforeCall(HttpRequest request) throws RuntimeException {
    }

    default void afterCall(HttpRequest request, HttpResponse response) throws RuntimeException {
    }
}
