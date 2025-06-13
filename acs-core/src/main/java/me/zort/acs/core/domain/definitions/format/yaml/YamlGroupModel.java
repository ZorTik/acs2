package me.zort.acs.core.domain.definitions.format.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;

import java.util.List;

@NoArgsConstructor
@Data
public class YamlGroupModel implements GroupDefinitionModel {
    private String name = null;
    private String parent;
    private List<String> nodes;

    @Override
    public String getParentName() {
        return parent;
    }
}
