package me.zort.acs.plane.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.plane.http.internal.resolver.RealmArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RealmArgumentResolver realmArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(realmArgumentResolver);
    }

    @Bean
    public AbstractJsonHttpMessageConverter messageConverter() {
        return new GsonHttpMessageConverter();
    }
}
