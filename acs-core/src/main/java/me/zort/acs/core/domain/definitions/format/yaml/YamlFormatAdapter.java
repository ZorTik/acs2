package me.zort.acs.core.domain.definitions.format.yaml;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormatAdapter;
import me.zort.acs.core.domain.definitions.format.yaml.model.YamlDefinitionsModel;
import me.zort.acs.core.domain.definitions.format.yaml.util.YamlFormatUtils;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class YamlFormatAdapter implements DefinitionsFormatAdapter {
    private static final Yaml YAML_LIB = YamlFormatUtils.createYaml();


    @Override
    public @NotNull DefinitionsModel parseModel(InputStream in) {
        return YAML_LIB.loadAs(in, YamlDefinitionsModel.class);
    }

    @Override
    public @NotNull String toStringModel(@NotNull DefinitionsModel model) {
        // TODO

        return "";
    }
}
