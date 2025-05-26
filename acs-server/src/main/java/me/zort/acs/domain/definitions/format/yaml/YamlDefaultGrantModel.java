package me.zort.acs.domain.definitions.format.yaml;

import lombok.Data;
import me.zort.acs.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;

@Data
public class YamlDefaultGrantModel implements DefaultGrantsDefinitionModel {
    private String from;
    private String to;
    private List<String> nodes;

    private YamlDefinitionsModel definitionsModel = null;

    protected void setYamlDefinitionsModel(YamlDefinitionsModel model) {
        this.definitionsModel = model;
    }

    @Override
    public SubjectTypeDefinitionModel getAccessorType() {
        return definitionsModel.getSubjectType(from);
    }

    @Override
    public SubjectTypeDefinitionModel getAccessedType() {
        return definitionsModel.getSubjectType(to);
    }

    @Override
    public List<String> getGrantedNodes() {
        return List.copyOf(nodes);
    }
}
