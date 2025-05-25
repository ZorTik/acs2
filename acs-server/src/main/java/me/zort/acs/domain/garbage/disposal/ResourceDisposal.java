package me.zort.acs.domain.garbage.disposal;

import me.zort.acs.domain.garbage.Disposable;

public interface ResourceDisposal<T extends Disposable> {

    void dispose(T resource);

    Class<T> getResourceType();

    default boolean shouldDispose(T resource) {
        return true;
    }

    default void disposeIfNecessary(T resource) {
        if (shouldDispose(resource)) {
            dispose(resource);
        }
    }
}
