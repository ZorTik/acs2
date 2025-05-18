package me.zort.acs.domain.garbage.disposal;

public abstract class ResourceDisposal<T> {

    public abstract void dispose(T resource);

    public abstract boolean shouldDispose(T resource);

    public abstract Class<T> getResourceType();

    public final void disposeIfNecessary(T resource) {
        if (shouldDispose(resource)) {
            dispose(resource);
        }
    }
}
