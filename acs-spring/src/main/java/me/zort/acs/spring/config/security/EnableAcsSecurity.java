package me.zort.acs.spring.config.security;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AcsSecurityConfig.class)
public @interface EnableAcsSecurity {
}
