package me.zort.acs.plane.api.domain.realm;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.realm.exception.RealmNotExistsException;

public interface Realm {

    String getName();

    DefinitionsModel getDefinitionsModel();

    boolean exists();

    default void requireExists() {
        if (!exists()) {
            throw new RealmNotExistsException(getName());
        }
    }
}
