package me.zort.acs.http.internal.resolvers;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.http.dto.model.subject.SubjectDto;
import me.zort.acs.http.internal.annotation.SubjectRequestParam;
import me.zort.acs.http.mapper.HttpSubjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Argument resolver for controller method parameters annotated with
 * {@link me.zort.acs.http.internal.annotation.SubjectRequestParam}.
 * <p>
 * This resolver extracts and deserializes subject-related request parameters
 * and maps them to domain objects using the provided {@link HttpSubjectMapper}.
 * </p>
 * <p>
 * Supports parameters of type {@link me.zort.acs.api.domain.model.SubjectLike}
 * and {@link me.zort.acs.domain.model.Subject}.
 * </p>
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class SubjectArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Class<?>[] SUPPORTED_PARAMETER_TYPES = new Class[] {
            SubjectLike.class, Subject.class
    };

    private final HttpSubjectMapper subjectMapper;

    @Override
    public boolean supportsParameter(@NotNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SubjectRequestParam.class)
                && ArrayUtils.contains(SUPPORTED_PARAMETER_TYPES, parameter.getParameterType());
    }

    @Nullable
    private String[] deserializeValue(String serializedValue) {
        if (!serializedValue.contains(":")) {
            return null;
        }

        return serializedValue.split(":", 2);
    }

    @Override
    public Object resolveArgument(
            @NotNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SubjectRequestParam annot = parameter.getParameterAnnotation(SubjectRequestParam.class);
        assert annot != null;

        String serializedValue = webRequest.getParameter(annot.value());
        if (serializedValue == null) {
            if (annot.required()) {
                // Missing query parameter, but is required
                throw new MissingServletRequestParameterException(
                        annot.value(), parameter.getParameterType().getSimpleName());
            }

            return null;
        }

        String[] values = deserializeValue(serializedValue);

        if (values == null) {
            Throwable cause = new IllegalArgumentException("Invalid format for parameter: expected 'group:id'");

            throw new MethodArgumentTypeMismatchException(
                    serializedValue, String.class, annot.value(), parameter, cause);
        }

        return subjectMapper.toDomainOrNull(SubjectDto.builder()
                .group(values[0])
                .id(values[1]).build());
    }
}
