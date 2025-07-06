package me.zort.acs.domain.messaging;

import me.zort.acs.api.messaging.ProtoAdapterRegistry;
import me.zort.acs.proto.ProtoAdapter;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProtoAdapterRegistryImpl implements ProtoAdapterRegistry {
    private final Map<String, ProtoAdapter> adapters;

    public ProtoAdapterRegistryImpl() {
        this.adapters = new HashMap<>();
    }

    @Override
    public void registerAdapter(String channel, ProtoAdapter adapter) {
        if (adapters.containsKey(channel)) {
            throw new IllegalArgumentException("Adapter for channel '" + channel + "' already exists.");
        }

        adapters.put(channel, adapter);
    }

    @Override
    public @Nullable ProtoAdapter getAdapter(String channel) {
        return adapters.getOrDefault(channel, null);
    }
}
