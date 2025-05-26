package me.zort.acs.api.domain.definitions.source;

import me.zort.acs.api.domain.definitions.model.DefinitionsModel;

import java.io.IOException;

public interface DefinitionsSource {

    DefinitionsModel getModel() throws IOException;
}
