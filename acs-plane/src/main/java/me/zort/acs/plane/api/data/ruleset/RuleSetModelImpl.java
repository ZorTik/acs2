package me.zort.acs.plane.api.data.ruleset;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RuleSetModelImpl implements RuleSetModel {
    private final String ruleSetId;
    private final String realmId;
    private final String name;
    private final String description;

}
