package me.zort.acs.core.domain.definitions.validation.visitor;

import me.zort.acs.core.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs.core.domain.definitions.validation.ValidationContext;

import java.util.Optional;

// 1
public class GroupReferencesVisitor implements DefinitionsVisitor {

    private void validateGroupReference(
            String group, SubjectTypeDefinitionModel subjectType, ValidationContext context) {
        context.getGroup(group, subjectType).orElseThrow(() -> new InvalidDefinitionsException(
                context.getModel(), String.format("Invalid group reference: %s", group)));
    }

    @Override
    public void visitGroup(ValidationContext context, GroupDefinitionModel group) throws InvalidDefinitionsException {
        String parentName = group.getParentName();
        if (parentName != null) {
            validateGroupReference(parentName, context.getSubjectType(), context);
        }
    }

    @Override
    public void visitDefaultGrant(
            ValidationContext context, DefaultGrantsDefinitionModel grant) throws InvalidDefinitionsException {
        for (String group : grant.getGrantedGroups()) {
            validateGroupReference(group, grant.getAccessedType(), context);
        }
    }
}
