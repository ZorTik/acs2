package me.zort.acs.domain.provider;

import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.provider.GrantProvider;
import me.zort.acs.domain.model.Grant;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class GrantProviderImpl implements GrantProvider, CacheDisposable {

    @Cacheable(
            value = "grants",
            key = "#options.accessor.subjectType.id + ':' " +
                    "+ #options.accessor.id + '->' " +
                    "+ #options.accessed.subjectType.id + ':' " +
                    "+ #options.accessed.id + '@' + #options.node.value")
    @Override
    public Grant getGrant(@NotNull GrantOptions options) {
        return new Grant(options.getAccessor(), options.getAccessed(), options.getNode());
    }

    @Override
    public String getCacheKey() {
        return "grants";
    }
}
