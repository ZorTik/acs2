package me.zort.acs.proto.plane;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.proto.MessageTypeEntry;
import me.zort.acs.proto.plane.message.DefinitionsChangedMessage;

@RequiredArgsConstructor
@Getter
public enum PlaneMessage implements MessageTypeEntry {

    DEFINITIONS_CHANGED("definitions_changed", DefinitionsChangedMessage.class);

    private final String key;
    private final Class<?> messageClass;
}
