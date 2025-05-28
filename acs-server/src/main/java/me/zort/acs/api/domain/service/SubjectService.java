package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;

import java.util.Optional;

public interface SubjectService {

    Subject createSubject(SubjectType type, String id);

    void deleteSubject(Subject subject);

    Optional<Subject> getSubject(SubjectType type, String id);

    boolean existsSubject(SubjectType type, String id);
}
