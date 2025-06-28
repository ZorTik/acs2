package me.zort.acs.plane.domain.definitions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlaneGroupDefinitionModel implements GroupDefinitionModel {
    private final String name;
    private final String parentName;
    private final List<String> nodes;

}
