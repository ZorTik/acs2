package me.zort.acs.client.http.model;

public interface NodeStatesResponse {

    boolean anyOf(String... nodes);

    boolean allOf(String... nodes);

    boolean all();
}
