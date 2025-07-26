package me.zort.acs.api.domain.group.exception;

public class GroupCreationDisallowedException extends RuntimeException {

    public GroupCreationDisallowedException(String message) {
        super(message);
    }
}
