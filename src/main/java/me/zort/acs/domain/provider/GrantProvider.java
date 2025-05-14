package me.zort.acs.domain.provider;

import lombok.RequiredArgsConstructor;
import me.zort.acs.config.properties.AcsConfigurationProperties;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.grant.GrantImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GrantProvider {
    private final AcsConfigurationProperties config;

    public Grant getGrant(Subject accessor, Subject accessed, Node node) {
        return new GrantImpl(accessor, accessed, node, config.getDelimiter());
    }
}
