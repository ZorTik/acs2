package me.zort.acs.api.domain.garbage.disposable;

import java.util.Set;

public interface CacheDisposable extends Disposable {

    Set<String> getCacheKeys();
}
