package me.zort.acs_plane.domain.definitions;

import lombok.AllArgsConstructor;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs_plane.api.domain.definitions.DefinitionsModification;
import me.zort.acs_plane.api.domain.definitions.DefinitionsObjectFactory;

@AllArgsConstructor
public class DefinitionsModificationImpl implements DefinitionsModification {
    private final DefinitionsObjectFactory objectFactory;
    private final DefinitionsModel model;

    // TODO: Modification methods impl
    // TODO: Záznamy modifikací
}
