package me.zort.acs.proto.plane.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DefinitionsChangedMessage {
    private final String definitions;
    // TODO: private final DefinitionsFormat format (need to move from acs-core to acs-api to use it here)

}
