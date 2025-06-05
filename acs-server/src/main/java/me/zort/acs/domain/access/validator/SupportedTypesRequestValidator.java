package me.zort.acs.domain.access.validator;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.grant.RightsAdapter;
import me.zort.acs.api.domain.model.SubjectLike;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class SupportedTypesRequestValidator implements AccessRequestValidator {
    private final RightsAdapter rightsAdapter;

    @Override
    public @Nullable String validate(SubjectLike from, SubjectLike to, RightsHolder rightsHolder) {
       try {
           rightsAdapter.requireSupportedType(rightsHolder);

           return null;
       } catch (IllegalArgumentException e) {
           return e.getMessage();
       }
    }
}
