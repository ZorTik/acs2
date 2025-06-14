package me.zort.acs.core.domain.definitions.format;

import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public interface DefinitionsFormatAdapter {

    @NotNull DefinitionsModel parseModel(InputStream in);

    @NotNull String toStringModel(@NotNull DefinitionsModel model);
}
