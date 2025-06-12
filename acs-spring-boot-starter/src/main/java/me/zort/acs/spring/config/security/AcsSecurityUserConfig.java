package me.zort.acs.spring.config.security;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("acs.security.user")
public class AcsSecurityUserConfig {
    private String subjectType;

}
