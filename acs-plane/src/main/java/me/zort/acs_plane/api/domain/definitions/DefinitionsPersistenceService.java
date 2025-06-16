package me.zort.acs_plane.api.domain.definitions;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

public interface DefinitionsPersistenceService {

    void saveDefinitions(String realmName, @Nullable DefinitionsModel model);

    @Unmodifiable
    @NotNull
    DefinitionsModel loadOrCreateDefinitions(String realmName);
}
