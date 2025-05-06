package me.zort.acs.domain.definitions;

import java.util.List;

public interface DefinitionsModel {

    List<SubjectTypeDefinition> getSubjectTypes();

    List<DefaultGrantsDefinition> getDefaultGrants();
}
