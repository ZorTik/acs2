package me.zort.acs.plane.api.domain.realm;

public interface RealmFactory {

    /**
     * Creates a new realm with the specified name.
     *
     * @param name the name of the realm to create
     * @return a new {@link Realm} instance with the specified name
     */
    Realm createRealm(String name);
}
