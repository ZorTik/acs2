package me.zort.acs.messaging.listener;

import lombok.SneakyThrows;
import me.zort.acs.api.domain.service.DefinitionsService;
import me.zort.acs.config.properties.AcsRealmConfigurationProperties;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.proto.plane.message.DefinitionsChangedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class DefinitionsChangedListener extends PlaneMessageListener<DefinitionsChangedMessage> {
    private final DefinitionsService definitionsService;

    @Autowired
    public DefinitionsChangedListener(
            AcsRealmConfigurationProperties realmConfig, DefinitionsService definitionsService) {
        super(realmConfig);
        this.definitionsService = definitionsService;
    }

    @SneakyThrows(IOException.class)
    @Override
    public void onMessage(DefinitionsChangedMessage message) {
        InputStream defsStream = new ByteArrayInputStream(message.getDefinitions().getBytes(StandardCharsets.UTF_8));
        try (defsStream) {
            DefinitionsModel model = message.getFormat().parseModel(defsStream);

            definitionsService.refreshDefinitions(model);
        }
    }

    @Override
    public Class<DefinitionsChangedMessage> getMessageType() {
        return DefinitionsChangedMessage.class;
    }
}
