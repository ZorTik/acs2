package me.zort.acs.plane.domain.definitions.object.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.core.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlaneDefinitionsModel implements DefinitionsModel {
    private final List<SubjectTypeDefinitionModel> subjectTypes;
    private final List<DefaultGrantsDefinitionModel> defaultGrants;

}
