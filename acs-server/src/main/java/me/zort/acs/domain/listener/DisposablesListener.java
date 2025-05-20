package me.zort.acs.domain.listener;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.garbage.DisposablesHolder;
import me.zort.acs.domain.garbage.ResourceDisposalService;
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
            disposalService.checkForGarbage(disposablesHolder);
        }
    }
}
