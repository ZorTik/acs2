package me.zort.acs.proto.plane.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.format.DefinitionsFormat;

@RequiredArgsConstructor
@Getter
public class DefinitionsChangedMessage {
    private final String definitions;
    private final DefinitionsFormat format;

}
