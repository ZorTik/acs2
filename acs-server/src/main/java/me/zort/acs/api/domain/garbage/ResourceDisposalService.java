package me.zort.acs.api.domain.garbage;

import me.zort.acs.core.domain.garbage.disposable.Disposable;

public interface ResourceDisposalService {

    void dispose(DisposablesHolder disposablesHolder);

    <T extends Disposable> void disposeBeans(Class<T> beanClass);
}
