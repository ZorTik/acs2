package me.zort.acs_plane.domain.realm.event;

import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.event.AbstractRealmEvent;

public class RealmCreatedEvent extends AbstractRealmEvent {

    public RealmCreatedEvent(Realm realm) {
        super(realm);
    }
}
