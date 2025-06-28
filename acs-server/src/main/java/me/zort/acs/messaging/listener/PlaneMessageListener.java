package me.zort.acs.messaging.listener;

import lombok.AllArgsConstructor;
import me.zort.acs.api.messaging.listener.ConvertedMessageListener;
import me.zort.acs.config.properties.AcsRealmConfigurationProperties;

@AllArgsConstructor
public abstract class PlaneMessageListener<T> implements ConvertedMessageListener<T> {
    private final AcsRealmConfigurationProperties realmConfig;

    @Override
    public String getChannel() {
        return realmConfig.getChannelNameForPlaneProto();
    }
}
