package me.zort.acs.domain.definitions;

import me.zort.acs.domain.definitions.model.DefinitionsModel;
import me.zort.acs.domain.definitions.model.YamlDefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public enum DefinitionsFormat {
    YAML {
        @Override
        public @NotNull DefinitionsModel parseModel(InputStream in) {
            Yaml yaml = new Yaml();

            return new YamlDefinitionsModel(yaml.load(in));
        }
    };

    public @NotNull DefinitionsModel parseModel(InputStream in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
