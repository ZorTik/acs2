package me.zort.acs.plane.config;

import me.zort.acs.plane.http.security.PlaneUserDetailsService;
import me.zort.acs.plane.http.internal.service.PathService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            PathService pathService, PlaneUserDetailsService userDetailsService) throws Exception {
        String loginPage = pathService.getLoginPage();

        return http
                // Meta
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .formLogin(form -> form
                        .loginPage(loginPage)
                        .defaultSuccessUrl(pathService.getPanelLandingPage()).permitAll())
                .logout(logout -> logout
                        .logoutUrl("/panel/logout")
                        .logoutSuccessUrl(loginPage).permitAll())
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/error", "/actuator/**").permitAll()
                        .requestMatchers(pathService.getApiPathPattern()).permitAll()
                        .requestMatchers(pathService.getLoginPage(), pathService.getRegisterPage()).permitAll()
                        .requestMatchers("/panel/realms/**").hasAuthority("EDIT_REALMS")
                        .requestMatchers(pathService.getPanelPathPattern()).authenticated()
                        .anyRequest().permitAll())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
