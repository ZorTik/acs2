package me.zort.acs.plane.domain.realm;

import lombok.*;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.realm.Realm;

@EqualsAndHashCode
@ToString(exclude = "exists")
public class RealmImpl implements Realm {
    @Getter
    private final String name;
    @Setter
    @Getter
    private DefinitionsModel definitionsModel;

    @Setter
    private boolean exists;

    protected RealmImpl(String name, DefinitionsModel definitionsModel) {
        this.name = name;
        this.definitionsModel = definitionsModel;
    }

    @Override
    public boolean exists() {
        return exists;
    }
}
