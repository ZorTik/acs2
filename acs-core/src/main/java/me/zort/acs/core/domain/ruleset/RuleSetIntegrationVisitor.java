package me.zort.acs.core.domain.ruleset;

import me.zort.acs.core.domain.access.rule.AccessRule;

public interface RuleSetIntegrationVisitor {

    /**
     * Visits an access rule within the rule set.
     *
     * @param accessRule The access rule to be visited
     */
    void visitAccessRule(AccessRule accessRule);
}
