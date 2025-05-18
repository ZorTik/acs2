package me.zort.acs.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.zort.acs.domain.garbage.Disposable;
import me.zort.acs.domain.garbage.DisposablesHolder;
import me.zort.acs.domain.model.Grant;

import java.util.List;

/**
 * An event holding a grant. This is configured to always trigger
 * disposables evict logic. If accessor or accessed don't contain links, they are evicted as a result of this
 * trigger.
 */
@AllArgsConstructor
@Getter
public abstract class GrantEvent implements DisposablesHolder {
    private final Grant grant;

    @Override
    public List<Disposable> getDisposables() {
        return List.of(grant.getAccessor(), grant.getAccessed());
    }
}
