package me.zort.acs.plane.config;

import me.zort.acs.plane.api.data.storage.BlobObjectRepository;
import me.zort.acs.plane.data.storage.FileSystemBlobObjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public BlobObjectRepository blobObjectRepository() {
        return new FileSystemBlobObjectRepository();
    }
}
