package me.zort.acs.domain.garbage;

import java.util.List;

/**
 * Holder of potential disposable objects that should be handled inside {@link ResourceDisposalService}.
 */
public interface DisposablesHolder {

    List<Disposable> getDisposables();
}
