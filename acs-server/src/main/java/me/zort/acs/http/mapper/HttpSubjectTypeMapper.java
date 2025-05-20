package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.service.SubjectTypeService;
import me.zort.acs.http.exception.ACSHttpException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HttpSubjectTypeMapper {
    private final SubjectTypeService subjectTypeService;

    public SubjectType toDomain(String id) {
        return subjectTypeService.getSubjectType(id)
                .orElseThrow(() -> new ACSHttpException("No such subject type: " + id, 404));
    }
}
