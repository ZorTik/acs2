package me.zort.acs.core.domain.definitions.validation.visitor;

import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.validation.ValidationContext;

// 1
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
