package me.zort.acs.plane.config;

import me.zort.acs.plane.http.util.PathUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Meta
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // Authentication
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers(PathUtils.PANEL_PATH_PATTERN, PathUtils.API_PATH_PATTERN, "/error").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
