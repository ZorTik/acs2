package me.zort.acs.api.domain.service;

import me.zort.acs.domain.model.SubjectType;

import java.util.List;
import java.util.Optional;

public interface SubjectTypeService {

    SubjectType createSubjectType(String id);

    void deleteSubjectType(SubjectType subjectType);

    Optional<SubjectType> getSubjectType(String id);

    List<SubjectType> getSubjectTypes();
}
