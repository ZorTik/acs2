package me.zort.acs.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LangConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource defaultMessageSource = new ResourceBundleMessageSource();
        defaultMessageSource.setBasename("lang/messages");
        defaultMessageSource.setDefaultEncoding("UTF-8");

        return defaultMessageSource;
    }
}
