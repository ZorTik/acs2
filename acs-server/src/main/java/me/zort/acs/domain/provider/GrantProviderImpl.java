package me.zort.acs.domain.provider;

import me.zort.acs.api.domain.provider.CachedProvider;
import me.zort.acs.api.domain.provider.GrantProvider;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.Subject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class GrantProviderImpl implements GrantProvider, CachedProvider {

    @Cacheable(value = "grants", key = "#accessor.subjectType.id + ':' + #accessor.id + '->' + #accessed.subjectType.id + ':' + #accessed.id + '@' + #node.value")
    @Override
    public Grant getGrant(Subject accessor, Subject accessed, Node node) {
        return new Grant(accessor, accessed, node);
    }

    @Override
    public String getCacheKey() {
        return "grants";
    }
}
