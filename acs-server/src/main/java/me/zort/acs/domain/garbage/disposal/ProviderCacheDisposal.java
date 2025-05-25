package me.zort.acs.domain.garbage.disposal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.api.domain.provider.CachedProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class ProviderCacheDisposal implements ResourceDisposal<CachedProvider> {
    private final CacheManager cacheManager;

    @Override
    public void dispose(CachedProvider resource) {
        String key = resource.getCacheKey();

        Cache cache = cacheManager.getCache(key);
        if (cache == null) {
            log.error("Could not dispose cache for provider. Cache does not exist. " +
                    "Key: {}", key);
            return;
        }

        cache.clear();
    }

    @Override
    public Class<CachedProvider> getResourceType() {
        return CachedProvider.class;
    }
}
