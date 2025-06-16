package me.zort.acs_plane.domain.realm.event;

import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.event.AbstractRealmEvent;

public class RealmDeletedEvent extends AbstractRealmEvent {

    public RealmDeletedEvent(Realm realm) {
        super(realm);
    }
}
