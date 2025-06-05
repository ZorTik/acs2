package me.zort.acs.domain.provider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.garbage.disposable.CacheDisposable;
import me.zort.acs.api.domain.grant.RightsAdapter;
import me.zort.acs.api.domain.model.Grant;
import me.zort.acs.api.domain.provider.GrantProvider;
import me.zort.acs.domain.provider.options.GrantOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class GrantProviderImpl implements GrantProvider, CacheDisposable {
    private final RightsAdapter rightsAdapter;

    @Cacheable(value = "grants", key = "#options.id")
    @Override
    public Grant getGrant(@Valid @NotNull GrantOptions options) {
        return rightsAdapter.createGrant(options);
    }

    @Override
    public Set<String> getCacheKeys() {
        return Set.of("grants");
    }
}
