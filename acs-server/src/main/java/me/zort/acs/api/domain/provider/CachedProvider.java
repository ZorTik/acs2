package me.zort.acs.api.domain.provider;

import me.zort.acs.domain.garbage.Disposable;

public interface CachedProvider extends Disposable {

    String getCacheKey();
}
