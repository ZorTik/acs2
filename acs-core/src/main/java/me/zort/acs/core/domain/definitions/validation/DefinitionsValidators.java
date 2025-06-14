package me.zort.acs.core.domain.definitions.validation;

import lombok.experimental.UtilityClass;
import me.zort.acs.core.domain.definitions.validation.visitor.CyclicGroupReferenceVisitor;
import me.zort.acs.core.domain.definitions.validation.visitor.DefinitionsVisitor;
import me.zort.acs.core.domain.definitions.validation.visitor.GroupReferencesVisitor;
import me.zort.acs.core.domain.definitions.validation.visitor.NodeReferencesVisitor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UtilityClass
public final class DefinitionsValidators {

    public static @NotNull DefinitionsValidator simple() {
        return simple(Collections.emptyList());
    }

    public static @NotNull DefinitionsValidator simple(List<DefinitionsVisitor> additionalVisitors) {
        return simple(ValidationContext::new, additionalVisitors);
    }

    public static @NotNull DefinitionsValidator simple(
            ObjectFactory<ValidationContext> contextFactory, List<DefinitionsVisitor> additionalVisitors) {
        Objects.requireNonNull(contextFactory, "contextFactory cannot be null");
        Objects.requireNonNull(additionalVisitors, "additionalVisitors cannot be null");

        List<DefinitionsVisitor> visitors = new ArrayList<>();
        visitors.add(new GroupReferencesVisitor());
        visitors.add(new NodeReferencesVisitor());
        visitors.add(new CyclicGroupReferenceVisitor());
        visitors.addAll(additionalVisitors);

        return new SimpleDefinitionsValidator(visitors, contextFactory);
    }
}
