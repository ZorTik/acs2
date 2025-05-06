package me.zort.acs.domain.rule;

import lombok.RequiredArgsConstructor;
import me.zort.acs.config.properties.AcsConfigurationProperties;
import me.zort.acs.domain.AccessRequest;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.service.GrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GrantsRule implements AccessRule {
    private final GrantService grantService;
    private final AcsConfigurationProperties properties;

    @Override
    public void onRequest(AccessRequest request) {
        List<Grant> grants = grantService.getGrants(request.getAccessor(), request.getAccessed());

        if (grants
                .stream()
                .anyMatch(grant -> grant.appliesTo(request.getNode(), properties.getDelimiter()))) {
            request.grant();
        }
    }
}
