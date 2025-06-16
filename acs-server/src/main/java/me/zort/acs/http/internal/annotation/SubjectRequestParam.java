package me.zort.acs.http.internal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for binding a method parameter to a subject-related request parameter
 * in Spring MVC controller methods.
 * <p>
 * Use this annotation on controller method parameters to extract subject-specific
 * values from HTTP requests, similar to {@code @RequestParam}.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * &#64;GetMapping("/subject")
 * public ResponseEntity&lt;?&gt; getSubject(
 *     &#64;SubjectRequestParam("subject") SubjectLike subject) {
 *     // ...
 * }
 * </pre>
 * </p>
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubjectRequestParam {

    /**
     * The name of the request parameter to bind to.
     *
     * @return the parameter name
     */
    String value();

    /**
     * Whether the parameter is required.
     * Defaults to {@code true}.
     *
     * @return {@code true} if the parameter is required, {@code false} otherwise
     */
    boolean required() default true;
}
