package me.zort.acs_plane.api.domain.cluster;

import me.zort.acs_plane.api.domain.realm.Realm;

/**
 * Publishes notification messages to nodes within a specific realm in the cluster.
 * <p>
 * Implementations of this interface are responsible for delivering typed notification messages
 * to all relevant nodes in the specified {@link Realm}.
 * </p>
 */
public interface ClusterNotificationPublisher {

    /**
     * Notifies nodes in the given realm with a message of the specified type.
     *
     * @param realm the realm to which the notification should be sent
     * @param typeIdentifier the class identifying the type of the message
     * @param message the notification message to send
     * @param <T> the type of the notification message
     */
    <T> void notifyNodes(Realm realm, Class<T> typeIdentifier, T message);
}
