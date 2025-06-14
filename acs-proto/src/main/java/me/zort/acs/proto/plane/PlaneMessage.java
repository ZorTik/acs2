package me.zort.acs.proto.plane;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.proto.MessageTypeEntry;

@RequiredArgsConstructor
@Getter
public enum PlaneMessage implements MessageTypeEntry {

    ; // TODO

    private final String key;
    private final Class<?> messageClass;
}
