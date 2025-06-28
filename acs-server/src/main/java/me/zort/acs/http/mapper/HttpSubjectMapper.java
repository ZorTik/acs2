package me.zort.acs.http.mapper;

import lombok.RequiredArgsConstructor;
import me.zort.acs.api.domain.service.SubjectService;
import me.zort.acs.api.http.exception.HttpExceptionFactory;
import me.zort.acs.domain.model.NullSubject;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.api.domain.model.SubjectLike;
import me.zort.acs.domain.model.SubjectType;
import me.zort.acs.http.dto.model.subject.SubjectDto;
import me.zort.acs.api.http.exception.ACSHttpException;
import me.zort.acs.api.http.exception.HttpException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Component
public class HttpSubjectMapper {
    private final HttpSubjectTypeMapper subjectTypeMapper;
    private final SubjectService service;
    private final HttpExceptionFactory exceptionProvider;

    public SubjectDto toHttp(SubjectLike subject) {
        return new SubjectDto(subject.getSubjectTypeId(), subject.getId());
    }

    public Subject toDomain(SubjectDto dto) {
        return toDomain(dto, false);
    }

    public Subject toDomain(SubjectDto dto, boolean createIfAbsent) {
        SubjectType type = subjectTypeMapper.toDomain(dto.getGroup());

        if (createIfAbsent && !service.existsSubject(type, dto.getId())) {
            return service.createSubject(type, dto.getId());
        } else {
            return service.getSubject(type, dto.getId())
                    .orElseThrow(() -> exceptionProvider.createException(HttpException.SUBJECT_NOT_FOUND, null, dto.getId()));
        }
    }

    /**
     * Maps subject dto to domain subject, or domain NullObject if it does not exist.
     * The difference can be easily checked using {@link NullSubject#isNull()}.
     *
     * @param dto The dto to map
     * @return The Subject, or NullSubject, depending on it presence in the system
     */
    @NotNull
    public SubjectLike toDomainOrNull(SubjectDto dto) {
        try {
            return toDomain(dto);
        } catch (ACSHttpException e) {
            if (e.isErrorSpecific() && e.getErrorCode() == HttpException.SUBJECT_NOT_FOUND.getErrorCode()) {
                SubjectType type = subjectTypeMapper.toDomain(dto.getGroup());

                return new NullSubject(type, dto.getId());
            } else {
                throw e;
            }
        }
    }
}
