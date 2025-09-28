package me.zort.acs.plane.config;

import me.zort.acs.plane.api.data.storage.BlobObjectRepository;
import me.zort.acs.plane.api.domain.storage.BlobPathConfig;
import me.zort.acs.plane.api.domain.storage.BlobStorageValidationService;
import me.zort.acs.plane.data.storage.FileSystemBlobObjectRepository;
import me.zort.acs.plane.domain.storage.validator.RuleSetValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public BlobObjectRepository blobObjectRepository() {
        return new FileSystemBlobObjectRepository();
    }

    @Bean
    public void addValidators(BlobStorageValidationService validationService, BlobPathConfig pathConfig) {
        validationService.addValidator(new RuleSetValidator(pathConfig));
    }
}
