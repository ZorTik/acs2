package me.zort.acs.core.domain.definitions.format.yaml;

import me.zort.acs.core.domain.definitions.format.DefinitionsFormatAdapter;
import me.zort.acs.core.domain.definitions.format.yaml.model.YamlDefinitionsModel;
import me.zort.acs.core.domain.definitions.format.yaml.util.YamlFormatUtils;
import me.zort.acs.core.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter for parsing and serializing definitions in YAML format.
 */
public class YamlFormatAdapter implements DefinitionsFormatAdapter {
    private static final Yaml YAML_LIB = YamlFormatUtils.createYaml();

    @Override
    public @NotNull DefinitionsModel parseModel(InputStream in) {
        return YAML_LIB.loadAs(in, YamlDefinitionsModel.class);
    }

    @Override
    public @NotNull String toStringModel(@NotNull DefinitionsModel model) {
        Map<String, Object> types = new HashMap<>();
        List<Object> defaultGrants = new ArrayList<>();

        model.getSubjectTypes().forEach(subjectTypeModel ->
                types.put(subjectTypeModel.getId(), toStringTypeModel(subjectTypeModel)));
        model.getDefaultGrants().forEach(defaultGrantModel ->
                defaultGrants.add(toStringDefaultGrantModel(defaultGrantModel)));

        Map<String, Object> definitions = Map.of("types", types, "default-grants", defaultGrants);

        return YAML_LIB.dump(definitions);
    }

    private Map<String, Object> toStringTypeModel(SubjectTypeDefinitionModel model) {
        List<Object> nodes = new ArrayList<>();
        Map<String, Object> groups = new HashMap<>();

        model.getNodes().forEach(node -> nodes.add(node.getValue()));
        model.getGroups().forEach(group -> groups.put(group.getName(), toStringGroupModel(group)));

        return Map.of("nodes", nodes, "groups", groups);
    }

    private Map<String, Object> toStringGroupModel(GroupDefinitionModel model) {
        Map<String, Object> group = new HashMap<>();
        String parentName = model.getParentName();

        if (parentName != null) {
            group.put("parent", parentName);
        }
        group.put("nodes", List.copyOf(model.getNodes()));

        return group;
    }

    private Map<String, Object> toStringDefaultGrantModel(DefaultGrantsDefinitionModel model) {
        Map<String, Object> grant = new HashMap<>();
        grant.put("from", model.getAccessorType().getId());
        grant.put("to", model.getAccessedType().getId());
        grant.put("nodes", List.copyOf(model.getGrantedNodes()));
        grant.put("groups", List.copyOf(model.getGrantedGroups()));

        return grant;
    }
}
