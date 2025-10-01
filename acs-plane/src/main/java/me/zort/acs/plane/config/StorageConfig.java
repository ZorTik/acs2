package me.zort.acs.plane.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.data.storage.BlobObjectRepository;
import me.zort.acs.plane.config.properties.StorageConfigurationProperties;
import me.zort.acs.plane.data.storage.FileSystemBlobObjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {
    private final StorageConfigurationProperties properties;

    @Bean
    public BlobObjectRepository blobObjectRepository() throws IOException {
        File storageDir = new File(properties.getLocation());
        Files.createDirectories(storageDir.toPath());

        return new FileSystemBlobObjectRepository(storageDir);
    }
}
