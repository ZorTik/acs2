package me.zort.acs.domain.definitions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SubjectTypeDefinition {
    private final String id;
    private final List<String> nodes;

}
