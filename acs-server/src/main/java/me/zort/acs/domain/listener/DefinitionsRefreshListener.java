package me.zort.acs.domain.listener;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.service.DefinitionsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class DefinitionsRefreshListener implements ApplicationListener<ApplicationReadyEvent> {
    private final DefinitionsService definitionsService;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        definitionsService.refresh();
    }
}