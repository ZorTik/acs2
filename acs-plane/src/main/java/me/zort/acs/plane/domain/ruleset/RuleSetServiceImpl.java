package me.zort.acs.plane.domain.ruleset;

import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.RuleSetService;
import me.zort.acs.plane.api.domain.ruleset.exception.RuleSetAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleSetServiceImpl implements RuleSetService {
    // TODO: Repository

    @Override
    public void registerRuleSet(String realmId, RuleSet ruleSet) throws RuleSetAlreadyExistsException {
        // TODO
    }

    @Override
    public void unregisterRuleSet(String realmId, String id) {
        // TODO
    }

    @Override
    public List<RuleSet> getRuleSets(String realmId) {
        // TODO
    }
}
