package me.zort.acs.domain.definitions.format.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.api.domain.definitions.model.GroupDefinitionModel;

import java.util.List;

@NoArgsConstructor
@Data
public class YamlGroupModel implements GroupDefinitionModel {
    private List<String> nodes;

}
