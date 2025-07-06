package me.zort.acs.api.domain.subjecttype;

import me.zort.acs.api.domain.subjecttype.exception.SubjectTypeAlreadyExistsException;
import me.zort.acs.domain.model.Node;
import me.zort.acs.domain.model.SubjectType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubjectTypeService {

    SubjectType createSubjectType(String id, CreateSubjectTypeOptions options) throws SubjectTypeAlreadyExistsException;

    void assignNodes(String id, Collection<Node> nodes);

    void assignNodes(SubjectType subjectType, Collection<Node> nodes);

    void deleteSubjectType(String id);

    Optional<SubjectType> getSubjectType(String id);

    List<SubjectType> getSubjectTypes();
}
