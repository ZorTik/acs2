package me.zort.acs.api.domain.node.exception;

public class NodeAlreadyExistsException extends RuntimeException {

    public NodeAlreadyExistsException(String value) {
        super("Node with value '" + value + "' already exists.");
    }
}
