package me.zort.acs.plane.config;

import me.zort.acs.plane.api.domain.storage.BlobPathConfig;
import me.zort.acs.plane.api.domain.storage.BlobStorageValidationService;
import me.zort.acs.plane.domain.storage.validator.RuleSetValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public void addValidators(BlobStorageValidationService validationService, BlobPathConfig pathConfig) {
        validationService.addValidator(new RuleSetValidator(pathConfig));
    }
}
