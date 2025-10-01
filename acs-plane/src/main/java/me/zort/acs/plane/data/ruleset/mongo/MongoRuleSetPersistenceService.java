package me.zort.acs.plane.data.ruleset.mongo;

import me.zort.acs.plane.data.ruleset.AbstractRuleSetPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoRuleSetPersistenceService extends AbstractRuleSetPersistenceService<RuleSetDocument> {

    @Autowired
    public MongoRuleSetPersistenceService(MongoRuleSetRepository repository, MongoRuleSetMapper mapper) {
        super(repository, mapper);
    }
}
