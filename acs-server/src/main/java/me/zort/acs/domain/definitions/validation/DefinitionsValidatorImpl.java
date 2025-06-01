package me.zort.acs.domain.definitions.validation;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.definitions.model.DefinitionsModel;
import me.zort.acs.api.domain.definitions.validation.DefinitionsValidator;
import me.zort.acs.domain.definitions.exception.InvalidDefinitionsException;
import me.zort.acs.domain.definitions.validation.visitor.DefinitionsVisitor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class DefinitionsValidatorImpl implements DefinitionsValidator {
    private final List<DefinitionsVisitor> visitors;
    private final ObjectProvider<ValidationContext> contextProvider;

    @Override
    public void validateDefinitions(DefinitionsModel model) throws InvalidDefinitionsException {
        ValidationContext context = contextProvider.getObject();
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
