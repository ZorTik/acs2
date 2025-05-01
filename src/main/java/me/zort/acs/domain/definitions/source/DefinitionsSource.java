package me.zort.acs.domain.definitions.source;

import me.zort.acs.domain.definitions.definition.SubjectTypeDefinition;

import java.util.List;

public interface DefinitionsSource {

    List<SubjectTypeDefinition> getSubjectTypes();
}
