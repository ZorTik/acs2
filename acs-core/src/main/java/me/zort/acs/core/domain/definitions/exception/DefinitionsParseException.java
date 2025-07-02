package me.zort.acs.core.domain.definitions.exception;

import lombok.Getter;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;

@Getter
public class DefinitionsParseException extends RuntimeException {
    private final DefinitionsFormat format;

    public DefinitionsParseException(DefinitionsFormat format, Throwable cause) {
        super(String.format("Failed to parse definitions. Format: %s", format.name()), cause);
        this.format = format;
    }
}
