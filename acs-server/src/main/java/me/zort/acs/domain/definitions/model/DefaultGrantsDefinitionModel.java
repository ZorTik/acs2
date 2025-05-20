package me.zort.acs.domain.definitions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DefaultGrantsDefinitionModel {
    private final SubjectTypeDefinitionModel accessorType;
    private final SubjectTypeDefinitionModel accessedType;
    private final List<String> grantedNodes;

}
