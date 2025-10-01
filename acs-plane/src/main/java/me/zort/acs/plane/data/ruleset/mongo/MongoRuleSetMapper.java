package me.zort.acs.plane.data.ruleset.mongo;

import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.data.ruleset.RuleSetMapper;
import me.zort.acs.plane.domain.ruleset.RuleSetImpl;
import org.springframework.stereotype.Component;

@Component
public class MongoRuleSetMapper implements RuleSetMapper<RuleSetDocument> {

    @Override
    public RuleSet toDomain(RuleSetDocument data) {
        return new RuleSetImpl(data.getRuleSetId(), data.getName(), data.getDescription());
    }

    @Override
    public RuleSetDocument toData(RuleSet ruleSet, ToDataOptions options) {
        return new RuleSetDocument(options.getRealmId(), ruleSet.getId(), ruleSet.getName(), ruleSet.getDescription());
    }
}
