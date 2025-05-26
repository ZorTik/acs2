package me.zort.acs.api.domain.definitions.model;

import java.util.List;

public interface DefinitionsModel {

    List<SubjectTypeDefinitionModel> getSubjectTypes();

    List<DefaultGrantsDefinitionModel> getDefaultGrants();
}
