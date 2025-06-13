package me.zort.acs.core.domain.definitions.model;

import java.util.List;

public interface DefinitionsModel {

    List<SubjectTypeDefinitionModel> getSubjectTypes();

    List<DefaultGrantsDefinitionModel> getDefaultGrants();
}
