package me.zort.acs.plane.api.domain.cluster;

import me.zort.acs.plane.api.domain.realm.Realm;

/**
 * Publishes raw protocol messages to a cluster within a specific realm.
 * <p>
 * Implementations of this interface are responsible for delivering raw message strings
 * to the appropriate cluster nodes, identified by the given {@link Realm}.
 * </p>
 */
public interface ClusterRawMessagePublisher {

    /**
     * Publishes a raw message to the specified realm.
     *
     * @param realm the realm to which the message should be published
     * @param message the raw message string to publish
     */
    void publishRawMessage(Realm realm, String message);
}
