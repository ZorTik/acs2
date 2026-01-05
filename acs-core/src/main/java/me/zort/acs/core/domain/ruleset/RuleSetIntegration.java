package me.zort.acs.core.domain.ruleset;

import me.zort.acs.core.domain.access.rule.AccessRule;

import java.util.List;

/**
 * Interface representing the integration of a rule set into the system.
 *
 * @author ZorTik
 */
public interface RuleSetIntegration {

    /**
     * Returns the list of access rules defined in this rule set.
     *
     * @return List of access rules
     */
    List<AccessRule> provideAccessRules();
}
