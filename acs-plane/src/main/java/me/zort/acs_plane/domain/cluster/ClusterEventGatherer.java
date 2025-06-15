package me.zort.acs_plane.domain.cluster;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;
import me.zort.acs.proto.plane.message.DefinitionsChangedMessage;
import me.zort.acs_plane.api.domain.cluster.ClusterNotificationPublisher;
import me.zort.acs_plane.domain.definitions.event.DefinitionsSavedEvent;
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
    public void onDefinitionsChanged(DefinitionsSavedEvent event) {
        String definitions = event.getDefinitions() != null
                // TODO: Propagate format to DefinitionsChangedMessage
                ? DefinitionsFormat.YAML.toStringModel(event.getDefinitions())
                : null;

        DefinitionsChangedMessage message = new DefinitionsChangedMessage(definitions);

        clusterNotificationPublisher.notifyNodes(event.getRealm(), DefinitionsChangedMessage.class, message);
    }
}
