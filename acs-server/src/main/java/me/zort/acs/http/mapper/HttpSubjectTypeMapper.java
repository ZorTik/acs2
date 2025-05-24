package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.service.SubjectTypeService;
import me.zort.acs.api.http.exception.HttpExceptionProvider;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.http.exception.HttpException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpSubjectTypeMapper {
    private final SubjectTypeService subjectTypeService;
    private final HttpExceptionProvider exceptionProvider;

    public SubjectType toDomain(String id) {
        return subjectTypeService.getSubjectType(id)
                .orElseThrow(() -> exceptionProvider.getException(HttpException.SUBJECT_TYPE_NOT_FOUND, null, id));
    }
}
