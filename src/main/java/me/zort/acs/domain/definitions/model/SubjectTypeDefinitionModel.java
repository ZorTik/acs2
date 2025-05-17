package me.zort.acs.domain.definitions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SubjectTypeDefinitionModel {
    private final String id;
    private final List<String> nodes;

}
