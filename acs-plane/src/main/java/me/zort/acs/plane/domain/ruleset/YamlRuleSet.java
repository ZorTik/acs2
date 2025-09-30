package me.zort.acs.plane.domain.ruleset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.zort.acs.plane.api.domain.ruleset.RuleSet;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YamlRuleSet implements RuleSet {
    private String id;

    private String name;
    private String description;

}