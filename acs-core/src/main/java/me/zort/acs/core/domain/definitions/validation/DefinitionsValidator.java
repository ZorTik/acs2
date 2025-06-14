package me.zort.acs.core.domain.definitions.validation;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;

public interface DefinitionsValidator {

    void validateDefinitions(DefinitionsModel model) throws InvalidDefinitionsException;
}
