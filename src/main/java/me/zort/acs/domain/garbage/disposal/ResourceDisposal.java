package me.zort.acs.domain.garbage.disposal;

import me.zort.acs.domain.garbage.Disposable;

public abstract class ResourceDisposal<T extends Disposable> {

    public abstract void dispose(T resource);

    public abstract boolean shouldDispose(T resource);

    public abstract Class<T> getResourceType();

    public final void disposeIfNecessary(T resource) {
        if (shouldDispose(resource)) {
            dispose(resource);
        }
    }
}
