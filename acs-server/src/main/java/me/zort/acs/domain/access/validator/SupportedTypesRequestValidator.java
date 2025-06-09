package me.zort.acs.domain.access.validator;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.grant.RightsHolderTypeRegistry;
import me.zort.acs.api.domain.model.SubjectLike;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class SupportedTypesRequestValidator implements AccessRequestValidator {
    private final RightsHolderTypeRegistry rightsHolderTypeRegistry;

    @Override
    public @Nullable String validate(SubjectLike from, SubjectLike to, RightsHolder rightsHolder) {
       try {
           rightsHolderTypeRegistry.requireSupportedType(rightsHolder);

           return null;
       } catch (IllegalArgumentException e) {
           return e.getMessage();
       }
    }
}
