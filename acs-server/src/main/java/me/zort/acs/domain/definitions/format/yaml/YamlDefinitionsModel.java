package me.zort.acs.domain.definitions.format.yaml;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.domain.definitions.model.DefinitionsModel;
import me.zort.acs.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class YamlDefinitionsModel implements DefinitionsModel {
    private Map<String, YamlTypeModel> types;
    private List<YamlDefaultGrantModel> defaultGrants;

    private SubjectTypeDefinitionModel mapSubjectTypeModel(YamlTypeModel model, String id) {
        return new YamlTypeModelWrapper(model, id);
    }

    @Override
    public List<SubjectTypeDefinitionModel> getSubjectTypes() {
        return types.entrySet()
                .stream()
                .map(entry -> mapSubjectTypeModel(entry.getValue(), entry.getKey()))
                .toList();
    }

    @Override
    public List<DefaultGrantsDefinitionModel> getDefaultGrants() {
        return defaultGrants
                .stream()
                .peek(grant -> grant.setYamlDefinitionsModel(this))
                .map(grant -> (DefaultGrantsDefinitionModel) grant)
                .toList();
    }

    public SubjectTypeDefinitionModel getSubjectType(String id) {
        return types.containsKey(id) ? mapSubjectTypeModel(types.get(id), id) : null;
    }
}
