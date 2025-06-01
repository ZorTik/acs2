package me.zort.acs.domain.provider;

import me.zort.acs.api.domain.access.RightsHolder;
import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.provider.GrantProvider;
import me.zort.acs.domain.model.Group;
import me.zort.acs.domain.model.GroupGrant;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.NodeGrant;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GrantProviderImpl implements GrantProvider, CacheDisposable {

    @Cacheable(value = "grants", key = "#options.id")
    @Override
    public Grant getGrant(@NotNull GrantOptions options) {
        RightsHolder rightsHolder = options.getRightsHolder();

        if (rightsHolder instanceof Node node) {
            return new NodeGrant(options.getId(), options.getAccessor(), options.getAccessed(), node);
        } else if (rightsHolder instanceof Group group) {
            return new GroupGrant(options.getId(), options.getAccessor(), options.getAccessed(), group);
        }

        throw new IllegalArgumentException("Invalid rights holder: " + rightsHolder.getClass().getSimpleName());
    }

    @Override
    public Set<String> getCacheKeys() {
        return Set.of("grants");
    }
}
