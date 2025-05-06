package me.zort.acs.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.definitions.DefinitionsSource;
import me.zort.acs.domain.definitions.yaml.YamlDefinitionsSource;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileInputStream;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
public class DefinitionsConfig {
    private final Environment environment;

    @Bean
    public DefinitionsSource definitionsSource() {
        DefinitionsSource source = getFileDefinitionsSource();

        if (source == null) {
            throw new IllegalStateException("Invalid definitions source");
        }

        return source;
    }

    private @Nullable DefinitionsSource getFileDefinitionsSource() {
        String path = environment.getProperty("acs.definitions.source.file");

        if (path == null) {
            return null;
        }

        File file = new File(path);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return null;
        }

        return new YamlDefinitionsSource(() -> new FileInputStream(file));
    }
}
