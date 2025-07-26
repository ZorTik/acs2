package me.zort.acs.core.domain.definitions.format.yaml.util;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.Map;

public class YamlPropertyUtils extends PropertyUtils {

    @Override
    public Property getProperty(Class<?> type, String name) {
        if (isKebabCase(name)) {
            name = toCamelCase(name);
        }
        if (name.equals("default-grants")) {
            name = "defaultGrants";
        }

        return super.getProperty(type, name);
    }

    private static boolean isKebabCase(String name) {
        return name.matches("^[a-z]+(-[a-z]+)*$");
    }

    private static String toCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;

        for (char c : name.toCharArray()) {
            if (c == '-') {
                capitalizeNext = true;
            } else {
                result.append(capitalizeNext ? Character.toUpperCase(c) : c);
                capitalizeNext = false;
            }
        }

        return result.toString();
    }
}
