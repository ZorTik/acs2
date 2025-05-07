package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.domain.service.SubjectService;
import me.zort.acs.domain.service.SubjectTypeService;
import me.zort.acs.http.dto.model.subject.SubjectDto;
import me.zort.acs.http.exception.ACSHttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class HttpSubjectMapper {
    private final SubjectTypeService subjectTypeService;
    private final SubjectService service;

    public Subject toDomain(SubjectDto dto) {
        SubjectType type = subjectTypeService.getSubjectType(dto.getGroup())
                .orElseThrow(() -> new ACSHttpException("Subject type not found", 400));

        return service.getSubject(type, dto.getId())
                .orElseThrow(() -> new ACSHttpException("Subject not found", 400));
    }
}
