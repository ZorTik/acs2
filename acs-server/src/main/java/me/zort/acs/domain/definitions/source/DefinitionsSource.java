package me.zort.acs.domain.definitions.source;

import me.zort.acs.domain.definitions.model.DefinitionsModel;

import java.io.IOException;

public interface DefinitionsSource {

    DefinitionsModel getModel() throws IOException;
}
