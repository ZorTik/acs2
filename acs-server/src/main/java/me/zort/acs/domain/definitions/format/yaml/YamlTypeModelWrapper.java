package me.zort.acs.domain.definitions.format.yaml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.api.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.api.domain.definitions.model.NodeDefinitionModel;
import me.zort.acs.api.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;

@AllArgsConstructor
public class YamlTypeModelWrapper implements SubjectTypeDefinitionModel {
    private final YamlTypeModel model;
    @Getter
    private final String id;

    @Override
    public List<NodeDefinitionModel> getNodes() {
        return List.copyOf(model.getNodes());
    }

    @Override
    public List<GroupDefinitionModel> getGroups() {
        return List.copyOf(model.getGroups());
    }
}
