package me.zort.acs.plane.http.mapper;

import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.http.dto.model.ListedRuleSet;
import org.springframework.stereotype.Component;

@Component
public class HttpRuleSetMapper {

    /**
     * Map rule set to listed dto.
     *
     * @param ruleSet The rule set
     * @return The mapped dto
     */
    public ListedRuleSet toHttpListed(RuleSet ruleSet) {
        // TODO
    }
}
