package me.zort.acs.plane.config;

import jakarta.validation.Validator;
import me.zort.acs.core.domain.ruleset.RuleSetParser;
import me.zort.acs.core.domain.ruleset.DefaultRuleSetParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleSetConfig {

    @Bean
    public RuleSetParser ruleSetParser(Validator validator) {
        return new DefaultRuleSetParser(validator);
    }
}
