package me.zort.acs_plane.api.domain.realm.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs_plane.api.domain.realm.Realm;

@RequiredArgsConstructor
@Getter
public abstract class AbstractRealmEvent {
    private final Realm realm;

}
