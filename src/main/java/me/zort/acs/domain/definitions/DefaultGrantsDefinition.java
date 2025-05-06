package me.zort.acs.domain.definitions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DefaultGrantsDefinition {
    private final SubjectTypeDefinition accessorType;
    private final SubjectTypeDefinition accessedType;
    private final List<String> grantedNodes;

}
