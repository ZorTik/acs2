package me.zort.acs_plane.api.data.definitions;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface DefinitionsPersistenceService {

    void saveDefinitions(String realm, @Nullable DefinitionsModel model);

    Optional<DefinitionsModel> loadDefinitions(String realm);
}
