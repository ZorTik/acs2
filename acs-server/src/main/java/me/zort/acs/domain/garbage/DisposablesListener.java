package me.zort.acs.domain.garbage;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.garbage.ResourceDisposalService;
import me.zort.acs.api.domain.garbage.DisposablesHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DisposablesListener {
    private final ResourceDisposalService disposalService;

    @EventListener
    public void onEvent(Object event) {
        if (event instanceof DisposablesHolder disposablesHolder) {
            disposalService.dispose(disposablesHolder);
        }
    }
}
