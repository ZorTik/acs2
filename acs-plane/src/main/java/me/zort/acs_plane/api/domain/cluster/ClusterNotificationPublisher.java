package me.zort.acs_plane.api.domain.cluster;

import me.zort.acs_plane.api.domain.realm.Realm;

public interface ClusterNotificationPublisher {

    <T> void notifyNodes(Realm realm, Class<T> typeOfMessage, T message);
}
