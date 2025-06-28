package me.zort.acs.plane.domain.realm;

import lombok.Getter;
import lombok.Setter;
import me.zort.acs.plane.api.domain.realm.Realm;

public class RealmImpl implements Realm {
    @Getter
    private final String name;

    @Setter
    private boolean exists;

    protected RealmImpl(String name) {
        this.name = name;
    }

    @Override
    public boolean exists() {
        return exists;
    }
}
