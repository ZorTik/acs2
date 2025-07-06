package me.zort.acs.plane.api.domain.realm.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.realm.Realm;

@RequiredArgsConstructor
@Getter
public abstract class AbstractRealmEvent {
    private final Realm realm;

}
