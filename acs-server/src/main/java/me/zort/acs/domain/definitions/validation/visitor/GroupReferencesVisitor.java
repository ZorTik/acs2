package me.zort.acs.domain.definitions.validation.visitor;

import me.zort.acs.api.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.domain.definitions.validation.ValidationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class GroupReferencesVisitor implements DefinitionsVisitor {

    @Override
    public void visitGroup(ValidationContext context, GroupDefinitionModel group) throws InvalidDefinitionsException {
        String parentName = group.getParentName();
        if (parentName != null) {
            context.getGroup(parentName).orElseThrow(() -> new InvalidDefinitionsException(
                    context.getModel(), String.format("Invalid group reference: %s", parentName)));
        }
    }
}
