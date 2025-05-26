package me.zort.acs.domain.definitions.format.yaml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;

@AllArgsConstructor
public class YamlTypeModelWrapper implements SubjectTypeDefinitionModel {
    private final YamlTypeModel model;
    @Getter
    private final String id;

    @Override
    public List<String> getNodes() {
        return model.getNodes();
    }
}
