package me.zort.acs.plane.api.data.mapper;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.plane.api.data.ruleset.RuleSetModel;
import me.zort.acs.plane.api.domain.ruleset.RuleSet;

public interface DomainRuleSetMapper {

    /**
     * Convert data model to rule set domain object.
     *
     * @param model The data model
     * @return The domain object of the rule set
     */
    RuleSet toDomain(RuleSetModel model);

    /**
     * Convert rule set to data model.
     *
     * @param ruleSet The rule set to convert
     * @param options The options for the conversion
     * @return The rule set data model
     */
    RuleSetModel toPersistence(RuleSet ruleSet, ToModelOptions options);

    @Getter
    @Builder
    class ToModelOptions {
        private final String realmId;

    }
}
