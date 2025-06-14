package me.zort.acs_plane.domain.realm.event;

import me.zort.acs_plane.api.domain.realm.Realm;
import me.zort.acs_plane.api.domain.realm.event.RealmEvent;

public class RealmDeletedEvent extends RealmEvent {

    public RealmDeletedEvent(Realm realm) {
        super(realm);
    }
}
