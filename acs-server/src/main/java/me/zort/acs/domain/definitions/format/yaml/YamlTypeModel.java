package me.zort.acs.domain.definitions.format.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YamlTypeModel {
    private List<YamlNodeModel> nodes;
    private List<YamlGroupModel> groups;

}
