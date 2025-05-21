package me.zort.acs.client.http;

public interface HttpInterceptor {

    void intercept(HttpRequest request, HttpResponse response) throws RuntimeException;
}
