package me.zort.acs.core.domain.ruleset.exception;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import me.zort.acs.core.domain.ruleset.RuleSet;

import java.util.Set;

@Getter
public class InvalidRuleSetConfigException extends InvalidRuleSetException {
    private final Set<ConstraintViolation<RuleSet>> violations;

    public InvalidRuleSetConfigException(Set<ConstraintViolation<RuleSet>> violations) {
        super("The rule set configuration format is invalid.");
        this.violations = violations;
    }
}
