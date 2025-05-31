package me.zort.acs.domain.definitions.validation;

import lombok.Data;
import me.zort.acs.api.domain.definitions.model.DefinitionsModel;
import me.zort.acs.api.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.api.domain.definitions.model.SubjectTypeDefinitionModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Scope("prototype")
@Component
public class ValidationContext {
    private DefinitionsModel model;
    private SubjectTypeDefinitionModel subjectType = null;

    public Optional<GroupDefinitionModel> getGroup(String name) {
        requireSubjectType();

        return subjectType.getGroups()
                .stream()
                .filter(def -> def.getName().equals(name))
                .findFirst();
    }

    private void requireSubjectType() {
        if (subjectType == null) {
            throw new IllegalStateException("Subject type not set");
        }
    }
}
