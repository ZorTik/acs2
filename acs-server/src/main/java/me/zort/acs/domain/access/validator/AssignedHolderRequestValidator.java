package me.zort.acs.domain.access.validator;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.access.RightsHolderPresenceVerifier;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.SubjectType;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AssignedHolderRequestValidator implements AccessRequestValidator {
    private final RightsHolderPresenceVerifier presenceVerifier;

    @Override
    public @Nullable String validate(SubjectLike from, SubjectLike to, RightsHolder rightsHolder) {
        SubjectType toSubjectType = to.getSubjectType();
        if (!presenceVerifier.isPresentInSubjectType(toSubjectType, rightsHolder)) {
            return "Resource's subject type (" + toSubjectType.getId() + ") " +
                    "does not contain provided rights holder (" + rightsHolder.toString() + ")!";
        }

        return null;
    }
}
