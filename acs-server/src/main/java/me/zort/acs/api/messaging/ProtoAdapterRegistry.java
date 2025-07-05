package me.zort.acs.api.messaging;

import me.zort.acs.proto.ProtoAdapter;
import org.jetbrains.annotations.Nullable;

public interface ProtoAdapterRegistry {

    void registerAdapter(String channel, ProtoAdapter adapter);

    @Nullable
    ProtoAdapter getAdapter(String channel);
}
