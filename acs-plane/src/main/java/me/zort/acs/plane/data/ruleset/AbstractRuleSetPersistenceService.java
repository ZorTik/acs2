package me.zort.acs.plane.data.ruleset;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.data.ruleset.RuleSetPersistenceService;
import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.exception.RuleSetAlreadyExistsException;

import java.util.List;

@RequiredArgsConstructor
public class AbstractRuleSetPersistenceService<D> implements RuleSetPersistenceService {
    private final RuleSetRepository<D> repository;
    private final RuleSetMapper<D> mapper;

    @Override
    public void persistRuleSet(String realmId, RuleSet ruleSet) throws RuleSetAlreadyExistsException {
        D data = mapper.toData(ruleSet, RuleSetMapper.ToDataOptions.builder()
                .realmId(realmId)
                .build());
        if (repository.existsByRealmIdAndRuleSetId(realmId, ruleSet.getId())) {
            throw new RuleSetAlreadyExistsException();
        }

        repository.save(data);
    }

    @Override
    public void deleteRuleSet(String realmId, String id) {
        repository.deleteByRealmIdAndRuleSetId(realmId, id);
    }

    @Override
    public List<RuleSet> getRuleSets(String realmId) {
        return repository.findByRealmId(realmId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
