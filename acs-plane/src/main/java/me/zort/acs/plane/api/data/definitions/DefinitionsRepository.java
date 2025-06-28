package me.zort.acs.plane.api.data.definitions;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface DefinitionsRepository {

    void saveDefinitions(String realm, @Nullable DefinitionsModel model);

    Optional<DefinitionsModel> loadDefinitions(String realm);
}
