package me.zort.acs_plane.domain.realm;

import org.springframework.stereotype.Component;

/**
 * On any change that affects anything that would result to realm
 * being different from the one cached, this ensures that the realm in cache
 * is always up-to-date.
 */
@Component
public class ChangesOnRealmListener {

    // Event listeners
}
