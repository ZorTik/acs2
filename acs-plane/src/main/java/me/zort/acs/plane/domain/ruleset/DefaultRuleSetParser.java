package me.zort.acs.plane.domain.ruleset;

import me.zort.acs.plane.api.domain.ruleset.RuleSet;
import me.zort.acs.plane.api.domain.ruleset.RuleSetParser;
import me.zort.acs.plane.api.domain.ruleset.exception.MalformedRuleSetDataException;
import org.springframework.stereotype.Service;

@Service
public class DefaultRuleSetParser implements RuleSetParser {

    @Override
    public RuleSet parseRuleSet(byte[] data) throws MalformedRuleSetDataException {
        // TODO: Parse ruleset.yml from the jar
    }
}
