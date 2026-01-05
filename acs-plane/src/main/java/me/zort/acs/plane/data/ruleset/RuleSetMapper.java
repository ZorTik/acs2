package me.zort.acs.plane.data.ruleset;

import lombok.Builder;
import lombok.Getter;
import me.zort.acs.core.domain.ruleset.RuleSet;

public interface RuleSetMapper<D> {

    RuleSet toDomain(D data);

    D toData(RuleSet ruleSet, ToDataOptions options);

    @Getter
    @Builder
    class ToDataOptions {
        private final String realmId;

    }
}
