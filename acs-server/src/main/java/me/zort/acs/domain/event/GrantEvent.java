package me.zort.acs.domain.event;

import lombok.Getter;
import me.zort.acs.api.domain.garbage.disposable.Disposable;
import me.zort.acs.api.domain.garbage.DisposablesHolder;
import me.zort.acs.domain.model.Grant;

import java.util.Collections;
import java.util.List;

/**
 * An event holding a grant. This is configured to always trigger
 * disposables evict logic. If accessor or accessed don't contain links, they are evicted as a result of this
 * trigger.
 */
@Getter
public abstract class GrantEvent implements DisposablesHolder {
    private final Grant grant;
    private final boolean shouldDispose;

    public GrantEvent(Grant grant) {
        this(grant, false);
    }

    public GrantEvent(Grant grant, boolean shouldDispose) {
        this.grant = grant;
        this.shouldDispose = shouldDispose;
    }

    @Override
    public List<Disposable> getDisposables() {
        return shouldDispose
                ? List.of(grant.getAccessor(), grant.getAccessed())
                : Collections.emptyList();
    }
}
