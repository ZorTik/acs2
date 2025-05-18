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
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final SubjectService service;

    public Subject toDomain(SubjectDto dto) {
        SubjectType type = subjectTypeMapper.toDomain(dto.getGroup());

        return service.getSubject(type, dto.getId())
                .orElseThrow(() -> new ACSHttpException("Subject not found", 404, ACSHttpException.HTTP_MAPPER_SUBJECT_NOT_FOUND));
    }

    public Subject toDomainOrNull(SubjectDto dto) {
        try {
            return toDomain(dto);
        } catch (ACSHttpException e) {
            if (e.isErrorSpecific() && e.getErrorCode() == ACSHttpException.HTTP_MAPPER_SUBJECT_NOT_FOUND) {
                return null;
            } else {
                throw e;
            }
        }
    }

    public Subject toDomainOrCreate(SubjectDto dto) {
        Subject subject = toDomainOrNull(dto);
        if (subject == null) {
            SubjectType type = subjectTypeMapper.toDomain(dto.getGroup());

            subject = service.getSubject(type, dto.getId(), true).orElseThrow();
        }

        return subject;
    }
}
