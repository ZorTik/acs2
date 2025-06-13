package me.zort.acs.core.domain.definitions.validation.visitor;

import me.zort.acs.core.domain.definitions.model.DefaultGrantsDefinitionModel;
import me.zort.acs.core.domain.definitions.model.GroupDefinitionModel;
import me.zort.acs.core.domain.definitions.model.NodeDefinitionModel;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.validation.ValidationContext;

public interface DefinitionsVisitor {

    default void visitNode(
            ValidationContext context, NodeDefinitionModel node) throws InvalidDefinitionsException {
    }

    default void visitGroup(
            ValidationContext context, GroupDefinitionModel group) throws InvalidDefinitionsException {
    }

    default void visitDefaultGrant(
            ValidationContext context, DefaultGrantsDefinitionModel grant) throws InvalidDefinitionsException {
    }
}
