package me.zort.acs.api.domain.garbage.disposable;

public interface Disposable {
    // Marks an object as disposable. Purpose of this is to
    // prevent unintentional use of non-disposable objects in the
    // ResourceDisposalRegistry.
}
