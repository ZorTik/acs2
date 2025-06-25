package me.zort.acs_plane.domain.cluster;

import lombok.RequiredArgsConstructor;
import me.zort.acs_plane.api.domain.cluster.ClusterRawMessagePublisher;
import me.zort.acs_plane.api.domain.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class RedisRawMessagePublisher implements ClusterRawMessagePublisher {
    private final StringRedisTemplate redisTemplate;

    private static String channelForRealm(Realm realm) {
        return "acs:realm:"  + realm.getName();
    }

    @Override
    public void publishRawMessage(Realm realm, String message) {
        redisTemplate.convertAndSend(channelForRealm(realm), message);
    }
}
