package me.zort.acs_plane.domain.cluster;

import lombok.RequiredArgsConstructor;
import me.zort.acs_plane.api.domain.cluster.ClusterNotificationPublisher;
import me.zort.acs_plane.domain.definitions.event.DefinitionsChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class ClusterEventGatherer {
    private final ClusterNotificationPublisher clusterNotificationPublisher;

    @EventListener
    public void onDefinitionsChanged(DefinitionsChangedEvent event) {
        // TODO: Publish notification to nodes
    }
}
