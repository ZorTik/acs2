package me.zort.acs_plane.domain.cluster;

import lombok.RequiredArgsConstructor;
import me.zort.acs.proto.ProtoAdapter;
import me.zort.acs.proto.exception.MessageTypeNotDefinedException;
import me.zort.acs_plane.api.domain.cluster.ClusterNotificationPublisher;
import me.zort.acs_plane.api.domain.cluster.ClusterRawMessagePublisher;
import me.zort.acs_plane.api.domain.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class ClusterProtoNotificationPublisher implements ClusterNotificationPublisher {
    private final ProtoAdapter protoAdapter;
    private final ClusterRawMessagePublisher rawMessagePublisher;

    @Override
    public <T> void notifyNodes(Realm realm, Class<T> typeIdentifier, T message) {
        String raw;
        try {
            raw = protoAdapter.serialize(typeIdentifier, message);
        } catch (MessageTypeNotDefinedException e) {
            // Not a proto message
            // TODO: Log
            return;
        }

        rawMessagePublisher.publishRawMessage(realm, raw);
    }
}
