package me.zort.acs.domain.access.validator;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.api.domain.subject.SubjectLike;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AssignedHolderRequestValidator implements AccessRequestValidator {
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;

    @Override
    public @Nullable String validate(SubjectLike from, SubjectLike to, RightsHolder rightsHolder) {
        SubjectType toSubjectType = to.getSubjectType();

        boolean isPresentInSubjectType = rightsHolderTypeRegistry
                .castAndCallAdapter(rightsHolder, (holder, type) ->
                        type.isPresentInSubjectType(holder, toSubjectType));
        if (!isPresentInSubjectType) {
            return "Resource's subject type (" + toSubjectType.getId() + ") " +
                    "does not contain provided rights holder (" + rightsHolder.toString() + ")!";
        }

        return null;
    }
}
