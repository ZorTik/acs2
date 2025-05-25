package me.zort.acs.api.domain.garbage;

import me.zort.acs.domain.garbage.Disposable;
import me.zort.acs.domain.garbage.DisposablesHolder;

public interface ResourceDisposalService {

    void dispose(DisposablesHolder disposablesHolder);

    void dispose(Disposable disposable);

    <T extends Disposable> void disposeBeans(Class<T> beanClass);
}
