package me.zort.acs.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.repository.SubjectTypeRepository;
import me.zort.acs.domain.definitions.source.DefinitionsSource;
import me.zort.acs.domain.definitions.source.JpaDefinitionsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
public class DefinitionsConfig {
    private final SubjectTypeRepository subjectTypeRepository;

    @Bean
    public DefinitionsSource definitionsSource() {
        return new JpaDefinitionsSource(subjectTypeRepository);
    }
}
