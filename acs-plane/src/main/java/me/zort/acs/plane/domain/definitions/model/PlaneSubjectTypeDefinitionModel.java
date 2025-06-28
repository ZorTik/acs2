package me.zort.acs.plane.domain.definitions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.model.NodeDefinitionModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlaneSubjectTypeDefinitionModel implements SubjectTypeDefinitionModel {
    private final String id;
    private final List<NodeDefinitionModel> nodes;
    private final List<GroupDefinitionModel> groups;

}
