package me.zort.acs.plane.api.domain.realm.exception;

import lombok.Getter;

@Getter
public class RealmAlreadyExistsException extends RuntimeException {

    public RealmAlreadyExistsException() {
        super("Realm %s already exists.");
    }
}
