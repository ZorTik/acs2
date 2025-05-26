package me.zort.acs.api.domain.garbage;

import me.zort.acs.api.domain.garbage.disposable.Disposable;
import me.zort.acs.domain.garbage.disposal.ResourceDisposal;
import org.jetbrains.annotations.NotNull;

public interface ResourceDisposalRegistry {

    @NotNull
    <T extends Disposable> ResourceDisposal<T> getDisposalFor(T resource);
}
