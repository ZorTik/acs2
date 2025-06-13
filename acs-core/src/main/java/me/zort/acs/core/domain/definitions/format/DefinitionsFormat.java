package me.zort.acs.core.domain.definitions.format;

import me.zort.acs.core.domain.definitions.format.yaml.util.YamlFormatUtils;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.format.yaml.model.YamlDefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public enum DefinitionsFormat {
    YAML {
        @Override
        public @NotNull DefinitionsModel parseModel(InputStream in) {
            return YAML_LIB.loadAs(in, YamlDefinitionsModel.class);
        }
    };

    private static final Yaml YAML_LIB;

    static {
        YAML_LIB = YamlFormatUtils.createYaml();
    }

    public @NotNull DefinitionsModel parseModel(InputStream in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
