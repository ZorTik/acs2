package me.zort.acs.domain.definitions.format.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class YamlTypeModel {
    private List<YamlNodeModel> nodes;
    private Map<String, YamlGroupModel> groups = new HashMap<>();

}
