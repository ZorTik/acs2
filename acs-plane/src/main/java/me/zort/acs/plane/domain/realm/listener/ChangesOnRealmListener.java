package me.zort.acs.plane.domain.realm.listener;

import me.zort.acs.plane.domain.realm.RealmImpl;
import me.zort.acs.plane.domain.realm.event.RealmCreatedEvent;
import me.zort.acs.plane.domain.realm.event.RealmDeletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * On any change that affects anything that would result to realm
 * being different from the one cached, this ensures that the realm in cache
 * is always up-to-date.
 */
@Component
public class ChangesOnRealmListener {

    @EventListener
    public void onRealmCreated(RealmCreatedEvent event) {
        if (event.getRealm() instanceof RealmImpl realm) {
            realm.setExists(true);
        }
    }

    @EventListener
    public void onRealmDeleted(RealmDeletedEvent event) {
        if (event.getRealm() instanceof RealmImpl realm) {
            realm.setExists(false);
        }
    }
}
