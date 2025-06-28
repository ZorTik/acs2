package me.zort.acs.plane.domain.definitions.event;

import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.plane.api.domain.realm.Realm;
import me.zort.acs.plane.api.domain.realm.event.AbstractRealmEvent;

@Getter
public class DefinitionsModifiedEvent extends AbstractRealmEvent {
    private final DefinitionsModel before;
    private final DefinitionsModel after;

    public DefinitionsModifiedEvent(Realm realm, DefinitionsModel before, DefinitionsModel after) {
        super(realm);
        this.before = before;
        this.after = after;
    }
}
