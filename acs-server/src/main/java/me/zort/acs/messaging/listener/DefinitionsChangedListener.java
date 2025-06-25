package me.zort.acs.messaging.listener;

import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.config.properties.AcsRealmConfigurationProperties;
import me.zort.acs.proto.plane.message.DefinitionsChangedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefinitionsChangedListener extends PlaneMessageListener<DefinitionsChangedMessage> {
    private final DefinitionsService definitionsService;

    @Autowired
    public DefinitionsChangedListener(
            AcsRealmConfigurationProperties realmConfig, DefinitionsService definitionsService) {
        super(realmConfig);
        this.definitionsService = definitionsService;
    }

    @Override
    public void onMessage(DefinitionsChangedMessage message) {
        // TODO
    }

    @Override
    public Class<DefinitionsChangedMessage> getMessageType() {
        return DefinitionsChangedMessage.class;
    }
}
