package me.zort.acs.core.domain.ruleset;

import me.zort.acs.core.domain.ruleset.exception.InvalidRuleSetException;

public interface RuleSetParser {

    /**
     * Parses a rule set info from provided bytes representing JAR file.
     * This method also validates if the rule set file has the required schema.
     *
     * @param data The data of the jar file
     * @return The rule set info
     * @throws InvalidRuleSetException If the provided data are invalid
     */
    RuleSet parseRuleSet(byte[] data) throws InvalidRuleSetException;
}
