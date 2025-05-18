package me.zort.acs.domain.garbage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class ResourceDisposalService {
    private final ResourceDisposalRegistry registry;

    public void checkForGarbage(DisposablesHolder disposablesHolder) {
        disposablesHolder.getDisposables().forEach(this::checkForGarbage);
    }

    public void checkForGarbage(Disposable disposable) {
        registry.getDisposalFor(disposable).disposeIfNecessary(disposable);
    }
}
