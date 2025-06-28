package me.zort.acs.plane.api.domain.realm.exception;

public class RealmNotExistsException extends RuntimeException {

    public RealmNotExistsException(String realmName) {
        super(String.format("Realm %s does not exist.",  realmName));
    }
}
