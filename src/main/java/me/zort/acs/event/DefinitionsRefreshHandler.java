package me.zort.acs.event;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.service.DefinitionsRefreshService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DefinitionsRefreshHandler implements ApplicationListener<ApplicationContextInitializedEvent> {
    private final DefinitionsRefreshService refreshService;

    @Override
    public void onApplicationEvent(@NotNull ApplicationContextInitializedEvent event) {
        refreshService.refresh();
    }
}
