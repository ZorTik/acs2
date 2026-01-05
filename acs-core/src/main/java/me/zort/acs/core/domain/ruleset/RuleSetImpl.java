package me.zort.acs.core.domain.ruleset;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RuleSetImpl implements RuleSet {
    @NotEmpty(message = "RuleSet id cannot be empty")
    private String id;

    @NotEmpty(message = "RuleSet name cannot be empty")
    private String name;
    @NotNull(message = "RuleSet description cannot be null")
    private String description;

}