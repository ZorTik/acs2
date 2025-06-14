package me.zort.acs.core.domain.definitions.format;

import lombok.AllArgsConstructor;
import me.zort.acs.core.domain.definitions.format.yaml.YamlFormatAdapter;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

@AllArgsConstructor
public enum DefinitionsFormat implements DefinitionsFormatAdapter {
    YAML(new YamlFormatAdapter());

    private final DefinitionsFormatAdapter adapter;

    public static @Nullable DefinitionsFormat fromMimeType(String mimeType) {
        switch (mimeType) {
            case "application/x-yaml":
                return YAML;
        }

        return null;
    }

    @Override
    public @NotNull DefinitionsModel parseModel(InputStream in) {
        return adapter.parseModel(in);
    }

    @Override
    public @NotNull String toStringModel(@NotNull DefinitionsModel model) {
        return adapter.toStringModel(model);
    }
}
