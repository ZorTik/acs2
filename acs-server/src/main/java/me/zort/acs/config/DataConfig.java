package me.zort.acs.config;

import jakarta.persistence.AttributeConverter;
import me.zort.acs.core.data.util.UUIDBytesConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class DataConfig {

    @Bean
    public AttributeConverter<UUID, byte[]> uuidAttributeConverter() {
        return new UUIDBytesConverter();
    }
}
