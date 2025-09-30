package me.zort.acs.plane.http.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ListedRuleSet {
    private final String id;
    private final String name;
    private final String description;

}
