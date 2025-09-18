package me.zort.acs.plane.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.api.domain.security.Privilege;
import me.zort.acs.plane.http.internal.filter.ApiKeyFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final ApiKeyFilter apiKeyFilter;

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
                        .requestMatchers(pathService.getLoginPage(), pathService.getRegisterPage()).permitAll()
                        .requestMatchers("/panel/realms/**").hasAuthority(Privilege.EDIT_REALMS.getAuthority())
                        .requestMatchers("/panel/users/**").hasAuthority(Privilege.EDIT_USERS.getAuthority())
                        .requestMatchers("/panel/keys/**").hasAuthority(Privilege.EDIT_API_KEYS.getAuthority())
                        .requestMatchers(pathService.getApiPathPattern()).authenticated()
                        .requestMatchers(pathService.getPanelPathPattern()).authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
