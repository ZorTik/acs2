package me.zort.acs.domain.grant.exception;

import lombok.Getter;
import me.zort.acs.api.domain.model.Grant;

@Getter
public class GrantAlreadyExistsException extends RuntimeException {
    private final Grant existingGrant;

    public GrantAlreadyExistsException(Grant existingGrant) {
        super("Grant already exists!");
        this.existingGrant = existingGrant;
    }
}
