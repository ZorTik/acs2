package me.zort.acs.domain.definitions.format.yaml;

import lombok.Data;

import java.util.List;

@Data
public class YamlTypeModel {
    private List<String> nodes;

    public List<String> getNodes() {
        return List.copyOf(nodes);
    }
}
