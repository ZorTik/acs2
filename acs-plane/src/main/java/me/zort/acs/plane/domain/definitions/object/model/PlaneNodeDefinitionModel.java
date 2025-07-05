package me.zort.acs.plane.domain.definitions.object.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.NodeDefinitionModel;

@AllArgsConstructor
@Getter
public class PlaneNodeDefinitionModel implements NodeDefinitionModel {
    private final String value;

}
