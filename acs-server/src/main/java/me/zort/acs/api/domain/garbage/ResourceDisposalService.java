package me.zort.acs.api.domain.garbage;

import me.zort.acs.domain.garbage.Disposable;
import me.zort.acs.domain.garbage.DisposablesHolder;

public interface ResourceDisposalService {

    void checkForGarbage(DisposablesHolder disposablesHolder);

    void checkForGarbage(Disposable disposable);
}
