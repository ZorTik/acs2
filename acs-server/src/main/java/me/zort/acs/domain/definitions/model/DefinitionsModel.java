package me.zort.acs.domain.definitions.model;

import java.util.List;

public interface DefinitionsModel {

    List<SubjectTypeDefinitionModel> getSubjectTypes();

    List<DefaultGrantsDefinitionModel> getDefaultGrants();
}
