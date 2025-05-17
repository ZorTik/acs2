package me.zort.acs.domain.definitions.model;

import me.zort.acs.domain.definitions.exception.IllegalDefinitionsFormatException;

import java.util.*;

@SuppressWarnings("unchecked")
public class YamlDefinitionsModel implements DefinitionsModel {
    private final Map<String, SubjectTypeDefinitionModel> subjectTypeDefinitions;
    private final List<DefaultGrantsDefinitionModel> defaultGrantsDefinitions;
    private final Set<String> nodes;

    public YamlDefinitionsModel(Map<String, Object> data) {
        this.subjectTypeDefinitions = new HashMap<>();
        this.defaultGrantsDefinitions = new ArrayList<>();
        this.nodes = new HashSet<>();

        populateSubjectTypeDefinitions((Map<String, Object>) data.get("types"));
        populateDefaultGrantsDefinitions((List<Object>) data.get("default-grants"));
    }

    private void populateSubjectTypeDefinitions(Map<String, Object> data) {
        for (String key : data.keySet()) {
            Map<String, Object> typeData = (Map<String, Object>) data.get(key);

            SubjectTypeDefinitionModel def = new SubjectTypeDefinitionModel(key, (List<String>) typeData.get("nodes"));

            subjectTypeDefinitions.put(key, def);
            nodes.addAll(def.getNodes());
        }
    }

    private void populateDefaultGrantsDefinitions(List<Object> data) {
        data.forEach(obj -> {
            Map<String, Object> grantData = (Map<String, Object>) obj;

            String fromKey = (String) grantData.get("from");
            String toKey = (String) grantData.get("to");

            SubjectTypeDefinitionModel fromDef = subjectTypeDefinitions.get(fromKey);
            SubjectTypeDefinitionModel toDef = subjectTypeDefinitions.get(toKey);

            String illegalKey = null;
            if (fromDef == null) {
                illegalKey = fromKey;
            } else if (toDef == null) {
                illegalKey = toKey;
            }

            if (illegalKey != null) {
                throw new IllegalDefinitionsFormatException("Invalid subject: " + illegalKey);
            }

            List<String> nodes = new ArrayList<>();
            for (String node : ((List<String>) grantData.get("nodes"))) {
                if (!this.nodes.contains(node)) {
                    throw new IllegalDefinitionsFormatException("Invalid node: " + node);
                }

                nodes.add(node);
            }

            defaultGrantsDefinitions.add(new DefaultGrantsDefinitionModel(fromDef, toDef, nodes));
        });
    }

    @Override
    public List<SubjectTypeDefinitionModel> getSubjectTypes() {
        return List.copyOf(subjectTypeDefinitions.values());
    }

    @Override
    public List<DefaultGrantsDefinitionModel> getDefaultGrants() {
        return List.copyOf(defaultGrantsDefinitions);
    }
}
