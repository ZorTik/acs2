package me.zort.acs.client.http.exception;

import lombok.Getter;
import me.zort.acs.client.http.model.nodes.list.ListNodesQuery;

@Getter
public class InvalidListNodesQueryException extends RuntimeException {
    private final ListNodesQuery query;

    public InvalidListNodesQueryException(ListNodesQuery query) {
        super("Invalid ListNodesQuery.");
        this.query = query;
    }
}
