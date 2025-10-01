package me.zort.acs.plane.data.ruleset.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zort.acs.plane.api.data.ruleset.RuleSetModel;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "acs_rulesets")
public class RuleSetDocument implements RuleSetModel {
    private String realmId;
    private String ruleSetId;

    private String name;
    private String description;

}
