package me.zort.acs_plane.api.domain.realm.exception;

import lombok.Getter;
import me.zort.acs_plane.api.domain.realm.Realm;

@Getter
public class RealmAlreadyExistsException extends RuntimeException {
    private final Realm existingRealm;

    public RealmAlreadyExistsException(Realm existingRealm) {
        super(String.format("Realm %s already exists.", existingRealm.getName()));
        this.existingRealm = existingRealm;
    }
}
