package me.zort.acs.client.http.adapter;

import me.zort.acs.client.http.HttpRequest;
import me.zort.acs.client.http.HttpResponse;

public interface HttpAdapter {

    HttpResponse perform(HttpRequest request) throws Exception;
}
