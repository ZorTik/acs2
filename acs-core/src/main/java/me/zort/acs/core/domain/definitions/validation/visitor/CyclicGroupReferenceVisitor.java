package me.zort.acs.core.domain.definitions.validation.visitor;

import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.validation.ValidationContext;

import java.util.ArrayList;
import java.util.List;

// 2
public class CyclicGroupReferenceVisitor implements DefinitionsVisitor {

    @Override
    public void visitGroup(ValidationContext context, GroupDefinitionModel group) throws InvalidDefinitionsException {
        validateReferences(context, group, new ArrayList<>());
    }

    private static void validateReferences(ValidationContext context, GroupDefinitionModel group, List<String> visited) throws InvalidDefinitionsException {
        if (visited.contains(group.getName())) {
            throw new InvalidDefinitionsException(
                    context.getModel(),
                    String.format("Cyclic reference between groups: %s", String.join(", ", visited)));
        }

        visited.add(group.getName());

        if (group.getParentName() != null) {
            GroupDefinitionModel parent = context.getGroup(group.getParentName()).orElseThrow(); // ???

            validateReferences(context, parent, visited);
        }
    }
}
