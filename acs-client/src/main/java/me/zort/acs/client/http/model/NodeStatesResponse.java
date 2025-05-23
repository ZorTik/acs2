package me.zort.acs.client.http.model;

public interface NodeStatesResponse {

    boolean anyNodes(String... nodes);

    boolean allNodes(String... nodes);
}
