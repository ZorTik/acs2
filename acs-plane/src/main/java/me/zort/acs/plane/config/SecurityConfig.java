package me.zort.acs.plane.config;

import me.zort.acs.plane.api.domain.user.UserService;
import me.zort.acs.plane.domain.security.PlaneUserDetailsService;
import me.zort.acs.plane.http.internal.service.PathService;
import me.zort.acs.plane.http.security.PlaneAuthenticationEntryPoint;
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
            HttpSecurity http, UserService userService, PathService pathService) throws Exception {
        return http
                // Meta
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/error", "/actuator/**")
                        .permitAll())
                .exceptionHandling(handlingCustomizer -> handlingCustomizer
                        .authenticationEntryPoint(new PlaneAuthenticationEntryPoint(userService, pathService)))
                .build();
    }

    @Bean
    public SecurityFilterChain panelSecurityFilterChain(
            HttpSecurity http, PlaneUserDetailsService userDetailsService) throws Exception {
        String loginPage = "/panel/login";

        return http
                .securityMatcher(PathUtils.panelPathPattern())
                // Authentication
                .userDetailsService(userDetailsService)
                .formLogin(form -> form
                        .loginPage(loginPage)
                        .defaultSuccessUrl("/panel/realms").permitAll())
                .logout(logout -> logout
                        .logoutUrl("/panel/logout")
                        .logoutSuccessUrl(loginPage).permitAll())
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/panel/setup")
                        .permitAll())
                .build();
    }

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(PathUtils.apiPathPattern())
                // Authentication
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers(PathUtils.apiPathPattern()).permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
