package me.zort.acs.domain.definitions.definition;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SimpleDefinition implements SubjectTypeDefinition {
    private String id;
    private List<String> nodes;

}
