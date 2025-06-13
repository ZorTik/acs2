package me.zort.acs.core.domain.definitions.format.yaml;

import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.model.NodeDefinitionModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;
import java.util.Map;

public class YamlTypeModelWrapper implements SubjectTypeDefinitionModel {
    private final YamlTypeModel model;
    @Getter
    private final String id;
    private final List<GroupDefinitionModel> groups;

    public YamlTypeModelWrapper(YamlTypeModel model, String id) {
        this.model = model;
        this.id = id;
        this.groups = model.getGroups().entrySet()
                .stream()
                .peek(entry -> entry.getValue().setName(entry.getKey()))
                .map(Map.Entry::getValue)
                .map(groupModel -> (GroupDefinitionModel) groupModel)
                .toList();
    }

    @Override
    public List<NodeDefinitionModel> getNodes() {
        return List.copyOf(model.getNodes());
    }

    @Override
    public List<GroupDefinitionModel> getGroups() {
        return List.copyOf(groups);
    }
}
