package me.zort.acs_plane.domain.cluster;

import lombok.RequiredArgsConstructor;
import me.zort.acs_plane.api.domain.cluster.ClusterNotificationPublisher;
import me.zort.acs_plane.domain.definitions.event.DefinitionsChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Component responsible for gathering and processing events within the cluster.
 * <p>
 * This class listens for definition change events and ensures their propagation
 * to other nodes in the cluster using {@link ClusterNotificationPublisher}.
 * </p>
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class ClusterEventGatherer {
    private final ClusterNotificationPublisher clusterNotificationPublisher;

    @EventListener
    public void onDefinitionsChanged(DefinitionsChangedEvent event) {
        // TODO: Publish notification to nodes
    }
}
