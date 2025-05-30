package me.zort.acs.domain.definitions.format.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.api.domain.definitions.model.NodeDefinitionModel;

@NoArgsConstructor
@Data
public class YamlNodeModel implements NodeDefinitionModel {
    private String value;

}
