package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.service.SubjectTypeService;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.api.http.exception.HttpException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpSubjectTypeMapper {
    private final SubjectTypeService subjectTypeService;
    private final HttpExceptionFactory exceptionProvider;

    public SubjectType toDomain(String id) {
        return subjectTypeService.getSubjectType(id)
                .orElseThrow(() -> exceptionProvider.createException(HttpException.SUBJECT_TYPE_NOT_FOUND, null, id));
    }
}
