package me.zort.acs.plane.data.ruleset.mongo;

import me.zort.acs.plane.data.ruleset.RuleSetRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoRuleSetRepository extends MongoRepository<RuleSetDocument, String>, RuleSetRepository<RuleSetDocument> {

    void deleteByRealmIdAndRuleSetId(String realmId, String ruleSetId);

    boolean existsByRealmIdAndRuleSetId(String realmId, String ruleSetId);

    List<RuleSetDocument> findByRealmId(String realmId);
}
