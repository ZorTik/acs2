package me.zort.acs.core.domain.definitions.validation;

import lombok.RequiredArgsConstructor;
import me.zort.acs.core.domain.definitions.model.DefinitionsModel;
import me.zort.acs.core.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.core.domain.definitions.validation.visitor.DefinitionsVisitor;
import org.springframework.beans.factory.ObjectFactory;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SimpleDefinitionsValidator implements DefinitionsValidator {
    private final List<DefinitionsVisitor> visitors;
    private final ObjectFactory<ValidationContext> contextFactory;

    @Override
    public void validateDefinitions(DefinitionsModel model) throws InvalidDefinitionsException {
        ValidationContext context = contextFactory.getObject();
        context.setModel(model);

        model.getSubjectTypes().forEach(subjectType -> {
            context.setSubjectType(subjectType);

            subjectType.getGroups().forEach(group ->
                    callVisitors(visitor -> visitor.visitGroup(context, group)));
            subjectType.getNodes().forEach(node ->
                    callVisitors(visitor -> visitor.visitNode(context, node)));
        });
    }

    private void callVisitors(Consumer<DefinitionsVisitor> action) {
        visitors.forEach(action);
    }
}
