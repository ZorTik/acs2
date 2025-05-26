package me.zort.acs.domain.definitions.format.yaml;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

public class YamlPropertyUtils extends PropertyUtils {

    @Override
    public Property getProperty(Class<?> type, String name) {
        if (name.equals("default-grants")) {
            name = "defaultGrants";
        }

        return super.getProperty(type, name);
    }
}
