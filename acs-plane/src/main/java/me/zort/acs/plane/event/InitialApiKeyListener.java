package me.zort.acs.plane.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zort.acs.plane.api.domain.security.ApiKeyService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class InitialApiKeyListener implements ApplicationListener<ApplicationReadyEvent> {
    private final ApiKeyService apiKeyService;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        if (apiKeyService.countApiKeys() == 0) {
            String initialApiKey = apiKeyService.generateApiKey();

            printInitialApiKey(initialApiKey);
        }
    }

    private void printInitialApiKey(String apiKey) {
        log.info("---------------------------------------------");
        log.info(" Since this is your first start, an initial API key has been generated for you.");
        log.info(" You can use this API key to access the ACS Plane.");
        log.info(" Initial API Key: {}", apiKey);
        log.info("---------------------------------------------");
    }
}
