package me.zort.acs_plane.domain.cluster;

import me.zort.acs_plane.api.domain.cluster.ClusterRawMessagePublisher;
import me.zort.acs_plane.api.domain.realm.Realm;
import org.springframework.stereotype.Service;

@Service
public class RedisRawMessagePublisher implements ClusterRawMessagePublisher {
    // TODO: Redis

    @Override
    public void publishRawMessage(Realm realm, String message) {
        // TODO
    }
}
