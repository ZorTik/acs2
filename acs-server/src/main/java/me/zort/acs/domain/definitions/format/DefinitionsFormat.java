package me.zort.acs.domain.definitions.format;

import me.zort.acs.domain.definitions.model.DefinitionsModel;
import me.zort.acs.domain.definitions.format.yaml.YamlDefinitionsModel;
import me.zort.acs.domain.definitions.format.yaml.YamlPropertyUtils;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public enum DefinitionsFormat {
    YAML {
        @Override
        public @NotNull DefinitionsModel parseModel(InputStream in) {
            return YAML_LOADER.loadAs(in, YamlDefinitionsModel.class);
        }
    };

    private static final Yaml YAML_LOADER;

    static {
        YAML_LOADER = createYamlLoader();
    }

    private static Yaml createYamlLoader() {
        Constructor modelConstructor = new Constructor(YamlDefinitionsModel.class, new LoaderOptions());
        modelConstructor.setPropertyUtils(new YamlPropertyUtils());

        return new Yaml(modelConstructor);
    }

    public @NotNull DefinitionsModel parseModel(InputStream in) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
