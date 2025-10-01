package me.zort.acs.plane.domain.ruleset;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.data.ruleset.RuleSetPersistenceService;
import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.RuleSetService;
import me.zort.acs.plane.api.domain.ruleset.exception.RuleSetAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleSetServiceImpl implements RuleSetService {
    private final RuleSetPersistenceService persistenceService;

    @Override
    public void registerRuleSet(String realmId, RuleSet ruleSet) throws RuleSetAlreadyExistsException {
        persistenceService.persistRuleSet(realmId, ruleSet);
    }

    @Override
    public void unregisterRuleSet(String realmId, String id) {
        persistenceService.deleteRuleSet(realmId, id);
    }

    @Override
    public List<RuleSet> getRuleSets(String realmId) {
        return persistenceService.getRuleSets(realmId);
    }
}
