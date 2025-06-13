package me.zort.acs.core.domain.definitions.format.yaml.util;

import lombok.experimental.UtilityClass;
import me.zort.acs.core.domain.definitions.format.yaml.model.YamlDefaultGrantModel;
import me.zort.acs.core.domain.definitions.format.yaml.model.YamlDefinitionsModel;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

@UtilityClass
public final class YamlFormatUtils {

    public static @NotNull Yaml createYaml() {
        Constructor constructor = new Constructor(YamlDefinitionsModel.class, new LoaderOptions());
        constructor.setPropertyUtils(new YamlPropertyUtils());

        TypeDescription description = new TypeDescription(YamlDefinitionsModel.class);
        description.putListPropertyType("default-grants", YamlDefaultGrantModel.class);
        constructor.addTypeDescription(description);

        return new Yaml(constructor);
    }
}
