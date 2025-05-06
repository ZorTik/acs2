package me.zort.acs.domain.definitions;

import java.io.IOException;

public interface DefinitionsSource {

    DefinitionsModel getModel() throws IOException;
}
