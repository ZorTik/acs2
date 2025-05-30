package me.zort.acs.domain.definitions.exception;

import lombok.Getter;
import me.zort.acs.api.domain.definitions.model.DefinitionsModel;

@Getter
public class InvalidDefinitionsException extends RuntimeException {
    private final DefinitionsModel model;

    public InvalidDefinitionsException(DefinitionsModel model, String message) {
        super(message);
        this.model = model;
    }
}
