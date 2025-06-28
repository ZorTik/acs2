package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.request.AccessRequest;
import me.zort.acs.api.domain.access.AccessRequestFactory;
import me.zort.acs.api.domain.access.request.SubjectToSubjectAccessRequest;
import me.zort.acs.api.domain.access.rights.RightsHolder;
import me.zort.acs.api.domain.model.SubjectLike;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AccessRequestFactoryImpl implements AccessRequestFactory {
    private final AccessValidatorService accessValidatorService;

    @Override
    public @NotNull AccessRequest createAccessRequest(SubjectLike from, SubjectLike to, RightsHolder rightsHolder) {
        accessValidatorService.validate(from, to, rightsHolder);

        return new SubjectToSubjectAccessRequest(from, to, rightsHolder);
    }
}
