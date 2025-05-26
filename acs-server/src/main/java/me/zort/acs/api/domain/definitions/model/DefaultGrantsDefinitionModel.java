package me.zort.acs.api.domain.definitions.model;

import java.util.List;

public interface DefaultGrantsDefinitionModel {

    SubjectTypeDefinitionModel getAccessorType();

    SubjectTypeDefinitionModel getAccessedType();

    List<String> getGrantedNodes();
}
