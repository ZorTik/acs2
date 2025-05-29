package me.zort.acs.domain.access;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.access.AccessRequest;
import me.zort.acs.api.domain.access.AccessRequestFactory;
import me.zort.acs.domain.model.DefaultAccessRequest;
import me.zort.acs.domain.model.Node;
import me.zort.acs.api.domain.model.SubjectLike;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class AccessRequestFactoryImpl implements AccessRequestFactory {
    private final AccessValidatorService accessValidatorService;

    @Override
    public @NotNull AccessRequest createAccessRequest(
            SubjectLike from, SubjectLike to, Node node) {
        accessValidatorService.validate(from, to, node);

        return new DefaultAccessRequest(from, to, node);
    }
}
