package me.zort.acs.api.domain.definitions.validation;

import me.zort.acs.api.domain.definitions.model.DefinitionsModel;
import me.zort.acs.domain.definitions.exception.InvalidDefinitionsException;

public interface DefinitionsValidator {

    void validateDefinitions(DefinitionsModel model) throws InvalidDefinitionsException;
}
