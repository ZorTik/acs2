package me.zort.acs.client.http.model;

public interface StatesResponse {

    boolean anyOf(String... keys);

    boolean allOf(String... keys);

    boolean all();
}
