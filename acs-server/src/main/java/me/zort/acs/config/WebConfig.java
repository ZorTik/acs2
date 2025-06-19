package me.zort.acs.config;

import lombok.RequiredArgsConstructor;
import me.zort.acs.http.internal.resolvers.SubjectArgumentResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SubjectArgumentResolver subjectArgumentResolver;

    @Override
    public void addArgumentResolvers(@NotNull List<HandlerMethodArgumentResolver> resolvers) {
        // @Subject
        resolvers.add(subjectArgumentResolver);
    }

    @Override
    public void configureContentNegotiation(@NotNull ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON)
                .favorPathExtension(false)
                .favorParameter(false);
    }

    @Bean
    public GsonHttpMessageConverter messageConverter() {
        return new GsonHttpMessageConverter();
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer() {
        return resolver -> {
            resolver.setSizeParameterName("pageSize");
            resolver.setPageParameterName("page");
        };
    }
}
