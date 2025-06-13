package me.zort.acs.core.domain.definitions.exception;

import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;

@Getter
public class InvalidDefinitionsException extends RuntimeException {
    private final DefinitionsModel model;

    public InvalidDefinitionsException(DefinitionsModel model, String message) {
        super(message);
        this.model = model;
    }
}
