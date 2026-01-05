package me.zort.acs.core.domain.ruleset;

public interface RuleSet {

    /**
     * The unique identifier of the rule set.
     *
     * @return id
     */
    String getId();

    /**
     * The name of the rule set.
     *
     * @return name
     */
    String getName();

    /**
     * A brief description of the rule set.
     *
     * @return description
     */
    String getDescription();
}
