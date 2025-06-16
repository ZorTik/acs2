package me.zort.acs_plane.domain.definitions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlaneDefaultGrantsDefinitionModel implements DefaultGrantsDefinitionModel {
    private final SubjectTypeDefinitionModel accessorType;
    private final SubjectTypeDefinitionModel accessedType;
    private final List<String> grantedNodes;
    private final List<String> grantedGroups;

}
