package me.zort.acs_plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs_plane.api.domain.realm.Realm;
import org.jetbrains.annotations.Nullable;

public interface DefinitionsService {

    // TODO: Modify logic with validation
    void setDefinitions(Realm realm, @Nullable DefinitionsModel model) throws InvalidDefinitionsException;

    DefinitionsModel getDefinitions(Realm realm);
}
