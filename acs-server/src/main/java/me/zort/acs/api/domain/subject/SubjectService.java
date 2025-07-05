package me.zort.acs.api.domain.subject;

import me.zort.acs.domain.model.Subject;

import java.util.Optional;

public interface SubjectService {

    Subject createSubject(CreateSubjectArgs createArgs);

    void deleteSubject(Subject.Id id);

    Optional<Subject> getSubject(Subject.Id id);

    boolean existsSubject(Subject.Id id);
}
