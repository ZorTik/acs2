package me.zort.acs.messaging;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.messaging.ProtoAdapterRegistry;
import me.zort.acs.config.properties.AcsRealmConfigurationProperties;
import me.zort.acs.proto.MessageTypeEntries;
import me.zort.acs.proto.ProtoAdapter;
import me.zort.acs.proto.plane.PlaneProtocol;
import me.zort.acs.proto.serialization.ProtoSerializationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class ProtoRegistrar {
    private final ProtoAdapterRegistry adapterRegistry;
    private final ProtoSerializationStrategy<?> serializationStrategy;
    private final AcsRealmConfigurationProperties realmConfig;

    @PostConstruct
    public void registerAdapters() {
        registerPlaneAdapter();
    }

    private void registerPlaneAdapter() {
        String channelName = realmConfig.getChannelNameForPlaneProto();
        ProtoAdapter adapter = new ProtoAdapter(
                MessageTypeEntries.fromImplementingEnum(PlaneProtocol.getImplementingEnum()), serializationStrategy);

        adapterRegistry.registerAdapter(channelName, adapter);
    }
}
