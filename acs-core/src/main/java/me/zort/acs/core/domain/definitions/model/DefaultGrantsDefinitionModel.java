package me.zort.acs.core.domain.definitions.model;

import java.util.List;

public interface DefaultGrantsDefinitionModel {

    SubjectTypeDefinitionModel getAccessorType();

    SubjectTypeDefinitionModel getAccessedType();

    List<String> getGrantedNodes();

    List<String> getGrantedGroups();
}
