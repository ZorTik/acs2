package me.zort.acs.core.domain.definitions.format;

import lombok.AllArgsConstructor;
import me.zort.acs.core.domain.definitions.exception.DefinitionsParseException;
import me.zort.acs.core.domain.definitions.format.yaml.YamlFormatAdapter;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

/**
 * Enum representing supported formats for definitions.
 * <p>
 * Each format provides an adapter for parsing and serializing
 * {@link DefinitionsModel} instances.
 * </p>
 */
@AllArgsConstructor
public enum DefinitionsFormat implements DefinitionsFormatAdapter {
    YAML(new YamlFormatAdapter());

    private final DefinitionsFormatAdapter adapter;

    /**
     * Returns the corresponding {@link DefinitionsFormat} for the given MIME type.
     * <p>
     * If no matching format is found, returns {@code null}.
     * </p>
     *
     * @param mimeType the MIME type to look up
     * @return the matching {@link DefinitionsFormat}, or {@code null} if not found
     */
    public static @Nullable DefinitionsFormat fromMimeType(String mimeType) {
        switch (mimeType) {
            case "application/x-yaml":
                return YAML;
        }

        return null;
    }

    @Override
    public @NotNull DefinitionsModel parseModel(InputStream in) throws DefinitionsParseException {
        return adapter.parseModel(in);
    }

    @Override
    public @NotNull String toStringModel(@NotNull DefinitionsModel model) {
        return adapter.toStringModel(model);
    }
}
