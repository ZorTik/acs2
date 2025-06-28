package me.zort.acs.plane.api.domain.realm;

import me.zort.acs.plane.api.domain.realm.exception.RealmNotExistsException;

public interface Realm {

    String getName();

    boolean exists(); // TODO: V provideru bude link a pokud by realm byla smazána, eventem se nastaví na false

    default void requireExists() {
        if (!exists()) {
            throw new RealmNotExistsException(getName());
        }
    }
}
