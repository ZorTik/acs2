package me.zort.acs.domain.event;

import lombok.AllArgsConstructor;
import me.zort.acs.domain.garbage.Disposable;
import me.zort.acs.domain.garbage.DisposablesHolder;
import me.zort.acs.domain.model.Grant;

import java.util.List;

@AllArgsConstructor
public class GrantRemoveEvent implements DisposablesHolder {
    private final Grant grant;

    @Override
    public List<Disposable> getDisposables() {
        return List.of(grant.getAccessor());
    }
}
