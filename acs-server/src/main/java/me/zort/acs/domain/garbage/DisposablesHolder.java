package me.zort.acs.domain.garbage;

import java.util.List;

/**
 * Holder of potential disposable objects that should be handled inside {@link me.zort.acs.api.domain.garbage.ResourceDisposalService}.
 */
public interface DisposablesHolder {

    List<Disposable> getDisposables();
}
