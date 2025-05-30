package me.zort.acs.domain.definitions.validation;

import lombok.Data;
import me.zort.acs.api.domain.definitions.model.DefinitionsModel;
import me.zort.acs.api.domain.definitions.model.SubjectTypeDefinitionModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Scope("prototype")
@Component
public class ValidationContext {
    private DefinitionsModel model;
    private SubjectTypeDefinitionModel subjectType;

}
