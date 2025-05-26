package me.zort.acs.domain.definitions.format.yaml;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class YamlTypeModel {
    private List<String> nodes;

    public List<String> getNodes() {
        return List.copyOf(nodes);
    }
}
