package me.zort.acs.plane.config;

import me.zort.acs.plane.http.security.PlaneAuthenticationProvider;
import me.zort.acs.plane.http.util.PathUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, PlaneAuthenticationProvider planeAuthenticationProvider) throws Exception {
        return http
                // Meta
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // Authentication
                .authenticationProvider(planeAuthenticationProvider)
                .formLogin(form -> form
                        .loginPage("/panel/login")
                        .defaultSuccessUrl("/panel/definitions/raw").permitAll())
                .logout(logout -> logout
                        .logoutUrl("/panel/logout")
                        .logoutSuccessUrl("/panel/login").permitAll())
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers(PathUtils.API_PATH_PATTERN, "/error", "/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
