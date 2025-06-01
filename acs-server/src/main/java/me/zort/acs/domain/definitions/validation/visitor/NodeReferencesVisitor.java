package me.zort.acs.domain.definitions.validation.visitor;

import me.zort.acs.api.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.api.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.api.domain.definitions.model.SubjectTypeDefinitionModel;
import me.zort.acs.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.domain.definitions.validation.ValidationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(1)
@Component
public class NodeReferencesVisitor implements DefinitionsVisitor {

    @Override
    public void visitGroup(
            ValidationContext context, GroupDefinitionModel group) throws InvalidDefinitionsException {
        validateNodeReferences(context, context.getSubjectType(), group.getNodes());
    }

    @Override
    public void visitDefaultGrant(
            ValidationContext context, DefaultGrantsDefinitionModel grant) throws InvalidDefinitionsException {
        validateNodeReferences(context, grant.getAccessedType(), grant.getGrantedNodes());
    }

    private void validateNodeReferences(
            ValidationContext context, SubjectTypeDefinitionModel typeModel, List<String> values) {
        values.forEach(value -> {
            if (typeModel.getNodes()
                    .stream()
                    .noneMatch(nodeModel -> value.equals(nodeModel.getValue()))) {
                String error = String.format("Invalid node reference %s for type %s", value, typeModel.getId());

                throw new InvalidDefinitionsException(context.getModel(), error);
            }
        });
    }
}
