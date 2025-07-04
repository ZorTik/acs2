package me.zort.acs.plane.api.domain.realm;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;

public interface Realm {

    String getName();

    DefinitionsModel getDefinitionsModel();
}
