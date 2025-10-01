package me.zort.acs.plane.api.data.ruleset;

import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.exception.RuleSetAlreadyExistsException;

import java.util.List;

public interface RuleSetPersistenceService {

    /**
     * Register rule set in the repository.
     *
     * @param realmId The realm id
     * @param ruleSet The rule set
     * @throws RuleSetAlreadyExistsException if the rule set at the location already exists
     */
    void persistRuleSet(String realmId, RuleSet ruleSet) throws RuleSetAlreadyExistsException;

    /**
     * Unregister rule set from the repository.
     *
     * @param realmId The realm id
     * @param id The rule set id
     */
    void deleteRuleSet(String realmId, String id);

    /**
     * List all registered rule sets for a realm id.
     *
     * @param realmId The realm id
     * @return The list of rule sets
     */
    List<RuleSet> getRuleSets(String realmId);
}
