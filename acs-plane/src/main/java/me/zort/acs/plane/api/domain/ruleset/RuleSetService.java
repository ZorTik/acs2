package me.zort.acs.plane.api.domain.ruleset;

import me.zort.acs.core.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.exception.RuleSetAlreadyExistsException;

import java.util.List;

public interface RuleSetService {

    /**
     * Registers a rule set.
     *
     * @param realmId The realm id where to register the rule set
     * @param ruleSet The rule set to register
     * @throws RuleSetAlreadyExistsException If the rule set with the given id already exists
     */
    void registerRuleSet(String realmId, RuleSet ruleSet) throws RuleSetAlreadyExistsException;

    /**
     * Unregisters a rule set by the given id.
     *
     * @param realmId The realm id where to unregister the rule set
     * @param id The id of the rule set to unregister
     */
    void unregisterRuleSet(String realmId, String id);

    /**
     * Returns all registered rule sets.
     *
     * @param realmId The realm id of the collection
     * @return The rule sets
     */
    List<RuleSet> getRuleSets(String realmId);
}
