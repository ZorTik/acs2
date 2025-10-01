package me.zort.acs.plane.data.ruleset;

import java.util.List;

public interface RuleSetRepository<D> {

    D save(D ruleSet);

    void deleteByRealmIdAndRuleSetId(String realmId, String ruleSetId);

    boolean existsByRealmIdAndRuleSetId(String realmId, String ruleSetId);

    List<D> findByRealmId(String realmId);
}
