package me.zort.acs.plane.api.domain.ruleset;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RuleSet {
    private final String id;

    private final String name;
    private final String description;

}
