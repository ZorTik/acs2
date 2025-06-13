package me.zort.acs.core.domain.definitions.format.yaml.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.core.domain.definitions.model.NodeDefinitionModel;

@NoArgsConstructor
@Data
public class YamlNodeModel implements NodeDefinitionModel {
    private String value;

}
