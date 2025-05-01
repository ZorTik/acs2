package me.zort.acs.domain.service;

import lombok.RequiredArgsConstructor;
import me.zort.acs.data.id.SubjectId;
import me.zort.acs.data.repository.SubjectRepository;
import me.zort.acs.domain.mapper.DomainSubjectMapper;
import me.zort.acs.domain.model.Subject;
import me.zort.acs.domain.model.SubjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final DomainSubjectMapper subjectMapper;

    public Optional<Subject> getSubject(SubjectType type, String id) {
        return subjectRepository.findById(new SubjectId(id, type.getId())).map(subjectMapper::toDomain);
    }
}
